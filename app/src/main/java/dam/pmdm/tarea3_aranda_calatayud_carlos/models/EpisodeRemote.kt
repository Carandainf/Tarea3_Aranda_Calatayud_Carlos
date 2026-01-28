package dam.pmdm.tarea3_aranda_calatayud_carlos.models

data class EpisodeRemote(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
)

