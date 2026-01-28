package dam.pmdm.tarea3_aranda_calatayud_carlos.api

import retrofit2.http.GET
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.EpisodeResponse
import dam.pmdm.tarea3_aranda_calatayud_carlos.models.CharacterRemote
import retrofit2.http.Url


interface RickAndMortyApiService {

    // Obtengo la lista de episodios, uso "suspend" para que funcione de manera asincrona
    @GET("episode")
    suspend fun getEpisodes(): EpisodeResponse

    // Obtengo la URL de los personajes
    @GET
    suspend fun getCharacter(@Url url: String): CharacterRemote
}