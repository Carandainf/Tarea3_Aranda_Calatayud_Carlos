package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam.pmdm.tarea3_aranda_calatayud_carlos.R
import dam.pmdm.tarea3_aranda_calatayud_carlos.adapters.EpisodeAdapter
import dam.pmdm.tarea3_aranda_calatayud_carlos.api.RetrofitClient
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.FragmentEpisodiosBinding
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.Episode
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.EpisodeRemote
import kotlinx.coroutines.launch

class EpisodiosFragment : Fragment() {

    // configuro el binding para acceder al RecyclerView
    private var _binding: FragmentEpisodiosBinding? = null
    private val binding get() = _binding!!

    // configuro Firebase y una lista para hacer pruebas
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var adapter: EpisodeAdapter
    private var listaEpisodios = mutableListOf<Episode>()


    // inflo el XML
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // inflo el layout usando Binding
        _binding = FragmentEpisodiosBinding.inflate(inflater, container, false)
        return binding.root
    }

    // mostramos los datos
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // creo una lista de datos, para probar el código que llevo hasta ahora
//        listaEpisodios = mutableListOf(
//            Episode(1, "Pilot", "S01E01", "Dec 2, 2013", false, R.mipmap.ic_launcher),
//            Episode(2, "Lawnmower Dog", "S01E02", "Dec 9, 2013", false, R.mipmap.ic_launcher),
//            Episode(3, "Anatomy Park", "S01E03", "Dec 16, 2013", false, R.mipmap.ic_launcher)
//        )

        // conecto el adaptador al recycleView usando la variable de Clase
        adapter = EpisodeAdapter(listaEpisodios) { episodio ->
            val bundle = Bundle().apply {
                putInt("id", episodio.id)
                putString("name", episodio.name)
                putString("airDate", episodio.airDate)
                putString("episodeCode", episodio.episodeCode)
                putBoolean("isWatched", episodio.isWatched)
                putStringArrayList("characters", ArrayList(episodio.characters))
            }

            findNavController().navigate(
                R.id.action_nav_episodes_to_detalleEpisodioFragment,
                bundle
            )

        }
        binding.recyclerViewEpisodios.adapter = adapter
        binding.recyclerViewEpisodios.layoutManager = LinearLayoutManager(requireContext())

        // Menú de filtro
        configurarMenu()

        // pido datos en Internet
        cargarEpisodios()

    }

    private fun configurarMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_episodios, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter_all -> {
                        adapter.updateList(listaEpisodios)
                        true
                    }

                    R.id.filter_watched -> {
                        val episodiosVistos = listaEpisodios.filter { it.isWatched }
                        adapter.updateList(episodiosVistos)
                        true
                    }

                    else -> false

                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun cargarEpisodios() {
        lifecycleScope.launch {
            try {
                // llamo a la Api
                val response = RetrofitClient.instance.getEpisodes()

                // convierto la respuesta en una lista de episodios
                val listaConvertida = response.results.map { remote ->
                    Episode(
                        id = remote.id,
                        name = remote.name,
                        airDate = remote.air_date,
                        episodeCode = remote.episode,
                        isWatched = false, // De esto se encarga la BD
                        image = R.drawable.img_placeholder_item_episode,   // mi PlaceHolder
                        characters = remote.characters  // lista de personajes que participan en el episodio

                    )
                }
                // actualizo la lista de clase
                listaEpisodios.clear()
                listaEpisodios.addAll(listaConvertida)

                // sincronizo con la base de datos, para mostrar correctamente los episodios vistos, que están en mi BD
                sincronizarConFirestore()

                // aviso al adaptador
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Manejo de errores
                Toast.makeText(
                    requireContext(),
                    "Error al cargar los episodios: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun sincronizarConFirestore() {
        // capturo el Id del usuario logueado
        val userId = auth.currentUser?.uid ?: return

        // conecto con la base de datos y le pongo un listener (addSnapshotListener), de manera que se actualice la vista cuando se actualice la BD
        db.collection("usuarios").document(userId).collection("episodios_vistos")
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                // obtengo los códigos de los episodios que están a true
                val codigoVistos =
                    snapshot.documents.filter { it.getBoolean("viewed") == true }
                        .map { it.id } // el id es el código del episodio, que es mi episodeCode de mi Data class

                // ahora recorro la lista y actualizo los capitulos vistos
                listaEpisodios.forEachIndexed { index, episode ->
                    val estaVistoEnFirestore = codigoVistos.contains(episode.episodeCode)
                    if (episode.isWatched != estaVistoEnFirestore) {
                        listaEpisodios[index] = episode.copy(isWatched = estaVistoEnFirestore)
                        adapter.notifyItemChanged(index)
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



