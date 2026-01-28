package dam.pmdm.rickyandmortyapp

import java.io.Serializable

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>
) : Serializable

data class EpisodeResponse(
    val results: List<Episode>
): Serializable
data class Character(
    val id: Int,
    val name: String,
    val image: String, // URL de la foto
    val species: String,
    val status: String
) : Serializable