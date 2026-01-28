package dam.pmdm.tarea3_aranda_calatayud_carlos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.tarea3_aranda_calatayud_carlos.databinding.ItemEpisodeBinding
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.Episode

class EpisodeAdapter(private var episodes: List<Episode>, private val onClick: (Episode) -> Unit) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    // El ViewHolder (El contenedor)
    class EpisodeViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding =
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]

        holder.binding.apply {
            // asignamos el texto
            episodeName.text = episode.name
            episodeCode.text = episode.episodeCode
            episodeDate.text = episode.airDate

            // asignamos la imagen para probar estoy usando un placeholder de ejemplo
            episodeImage.setImageResource(episode.image)

            // indico si el capitulo se ha visto o no
            if (episode.isWatched) {
                ivWatched.visibility = View.VISIBLE
            } else {
                ivWatched.visibility = View.GONE
            }

            // detecto si pulso sobre algunas de las tarjetas
            root.setOnClickListener {

                // esto es una prueba, aquí en teoría lanzare la actividad de detalle del episodio
                //android.widget.Toast.makeText(root.context, "Clic en: ${episode.name}", android.widget.Toast.LENGTH_SHORT).show()

                onClick(episode)
            }
        }
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    fun updateList(newEpisodes: List<Episode>) {
        episodes = newEpisodes
        notifyDataSetChanged()
    }

}

