package dam.pmdm.tarea3_aranda_calatayud_carlos.models

// lo usare
data class Episode(
    val id: Int,
    val name: String,  // nombre del episodio
    val airDate: String,  // fecha de emisión
    val episodeCode: String,  // código del episodio
    val isWatched: Boolean = false, // indicador de si se ha visto
    val image: Int, // placeholder
    val characters: List<String> = emptyList() // lista de personajes que participan en el episodio, por defecto la creo vacía por seguridad
)
