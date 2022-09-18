package com.hvasoftware.wikifilm.model

data class Actor(
    val adult: Boolean,
    val gender: Int,
    val id: String,
    val known_for: MutableList<KnowFor>,
    val known_for_department: String,
    val popularity: Double,
    val profile_path: String,
    val place_of_birth: String,
    val homepage: String,
    val also_known_as: MutableList<String>,
    val biography: String,
    val deathday: String,
    val imdb_id: String,
    val birthday: String,
    val name: String
)

/*
{
"adult": false,
"also_known_as": [
],
"biography": "Seungha is a famous South Korean singer and ex-member of the K-pop girl group BaBa. Her birth name is Chae Seung-ha. She was both born and resides in Seoul. In 2020 she turned her life around, moving to the big screen with softcore films that are quite strong for a country like South Korea. Seungha's birth flower is Actinotus - Flannel Flower. She debuted in the group BaBa on July 03, 2018, at the age of 18.",
"birthday": "2000-04-17",
"deathday": null,
"gender": 1,
"homepage": null,
"id": 2710789,
"imdb_id": "nm13364301",
"known_for_department": "Acting",
"name": "Seung Ha",
"place_of_birth": "South Korea",
"popularity": 169.59,
"profile_path": "/8dCFK8FDFQbYFZvzAE9IIeaDMKo.jpg"
}
 */