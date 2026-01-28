package dam.pmdm.rickyandmortyapp

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("episode")
    suspend fun getEpisodes(): Response<EpisodeResponse>

    // Cambiado a Response para manejar errores mejor
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): Response<List<Character>>
}