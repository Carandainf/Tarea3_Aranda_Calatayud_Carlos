package dam.pmdm.tarea3_aranda_calatayud_carlos.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam.pmdm.tarea3_aranda_calatayud_carlos.adapters.CharacterAdapter
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.FragmentDetalleEpisodioBinding
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.Character
import dam.pmdm.tarea3_aranda_calatayud_carlos.api.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException


class DetalleEpisodioFragment : Fragment() {
    private var _binding: FragmentDetalleEpisodioBinding? = null
    private val binding get() = _binding!!

    // Inicializo la base de datos
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflo el layout para el fragmento sobre el que he hecho clic
        _binding = FragmentDetalleEpisodioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // leemos los datos que nos envía la interfaz (Bundle)
        val nombre = arguments?.getString("name") ?: "Episodio desconocido"
        val fecha = arguments?.getString("airDate") ?: "Sin fecha"
        val codigo = arguments?.getString("episodeCode") ?: "S00E00"
        val oldVisto = arguments?.getBoolean("isWatched") ?: false

        // mostramos los datos en el layout
        binding.detalleNombre.text = nombre
        binding.detalleFecha.text = fecha
        binding.detalleCodigo.text = codigo
        binding.swDetalleVisto.isChecked = oldVisto

        // capturo el Id del usuario logueado
        val userId = auth.currentUser?.uid

        // cargo estado de visto/no visto
        if (userId != null) {
            db.collection("usuarios").document(userId)
                .collection("episodios_vistos").document(codigo)
                .get().addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val newVisto = document.getBoolean("viewed") ?: false
                        // Actualizo el Switch
                        binding.swDetalleVisto.isChecked = newVisto
                    }
                    configurarListenerSwitch(userId, nombre, fecha, codigo)
                }.addOnFailureListener {
                    // Si hay algún fallo en la red el Listener lo preparamos
                    configurarListenerSwitch(userId, nombre, fecha, codigo)
                }

        }
        // configuro los personajes

//        // Datos de prueba para los personajes
//        val personajesPrueba = listOf(
//            Character(1, "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
//            Character(2, "Morty Smith", "https://rickandmortyapi.com/api/character/avatar/2.jpeg"),
//            Character(3, "Summer Smith", "https://rickandmortyapi.com/api/character/avatar/3.jpeg"),
//            Character(4, "Beth Smith", "https://rickandmortyapi.com/api/character/avatar/4.jpeg"),
//            Character(5, "Jerry Smith", "https://rickandmortyapi.com/api/character/avatar/5.jpeg"),
//            Character(6, "Mr. Poopybutthole", "https://rickandmortyapi.com/api/character/avatar/196.jpeg")
//        )

        // primero recuperamos las URL que tengo guardada en el Bundle. (EpisodiosFragment)
        val urlsPersonajes = arguments?.getStringArrayList("characters") ?: arrayListOf()

        // Configuro el adaptador, de momento vacío
        val charAdapter = CharacterAdapter(mutableListOf())

        // configuro el RecyclerView
        binding.rvPersonajes.apply {
            adapter = charAdapter
            // uso un GridLayout de dos columnas
            layoutManager = GridLayoutManager(requireContext(), 2)
            // para mejorar el rendimiento si el tamaño del grid no cambia
            setHasFixedSize(true)
        }
        // si tengo urls, las cargo
        if (urlsPersonajes.isNotEmpty()) {
            cargarPersonajesReales(urlsPersonajes)
        }

    }

    private fun configurarListenerSwitch(
        userId: String,
        nombre: String,
        fecha: String,
        codigo: String
    ) {
        // Recupero la lista de personaje
        val personajeUrls = arguments?.getStringArrayList("characters") ?: arrayListOf()

        // escucho si el usuario cambia el estado de visto/no visto del capítulo
        binding.swDetalleVisto.setOnCheckedChangeListener { _, isChecked ->
            val episodioData = hashMapOf(
                "name" to nombre,
                "air_date" to fecha,
                "episode" to codigo,
                "viewed" to isChecked,
                "characters" to personajeUrls
            )
            // guardamos datos en la BD
            db.collection("usuarios").document(userId)
                .collection("episodios_vistos").document(codigo)
                .set(episodioData)
                .addOnSuccessListener {
                    val mensaje = if (isChecked) "Visto" else "No visto"
                    Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

    }

    private fun cargarPersonajesReales(urls: List<String>) {
        viewLifecycleOwner.lifecycleScope.launch {
            // si cancela la petición por ejemplo saliendo de la pantalla, cancelamos la petición de datos a la Web
            try {
                val personajes = mutableListOf<Character>()
                // ahora recorro la lista de urls y pido la URL de una en una
                urls.forEach { url ->
                    ensureActive() // Compruebo si el usuario sigue en la pantalla
                    try {
                        // le meto un retardo entre peticiones, debido a que me salta un error 429 si las hago muy rápido
                        kotlinx.coroutines.delay(150)

                        val remote = RetrofitClient.instance.getCharacter(url)
                        personajes.add(
                            Character(
                                id = remote.id,
                                name = remote.name,
                                image = remote.image
                            )
                        )
                        // actualizo la lista de personajes según me van llegando, por si se produce un error de carga
                        // tener por lo menos los primeros que he leido
                        binding.rvPersonajes.adapter = CharacterAdapter(personajes)

                    } catch (e: Exception) {
                        // al probar la app, si alguna dirección de un personaje fallaba en la carga, me rompía la App, de esta manera solo
                        // falla ese personaje. También tenía problemas con el error 429 si el usuario se salía muy rápido de la pantalla
                        Log.e("API", "Error al cargar el personaje: ${e.message}")
                        if (e.message?.contains("429") == true) {
                            kotlinx.coroutines.delay(1000)
                        }
                    }
                }
            } catch (e: Exception) {
                // al probar la app, si alguna dirección de un personaje fallaba en la carga, me rompía la App, de esta manera solo falla esa carga
                if (e !is CancellationException) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}