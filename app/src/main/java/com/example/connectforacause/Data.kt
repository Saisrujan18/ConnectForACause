package com.example.connectforacause
//Also Image need to be stored
data class Activity_Data(
    val title:String,
    val description:String
)
data class Description_Data(
    val description: String
)

data class OrganisationTileInfo(
    val Activities: List<String>,
    val Description: String = "",
    val Photo: String = "",
    val Title: String = "",
)
