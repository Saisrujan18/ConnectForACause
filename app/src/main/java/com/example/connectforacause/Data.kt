package com.example.connectforacause

import java.io.Serializable

//Also Image need to be stored
data class Activity_Data(
    val title:String,
    val description:String
)
data class Description_Data(
    val description: String
)

data class OrganisationTileInfo(
    val Activities: MutableList<String> = mutableListOf(),
    val Description: String = "",
    val Photo: String = "",
    val Title: String = "",
)

data class ActivityInfo(
    val Volunteers: ArrayList<String> = ArrayList(),
    val Count: Int = 0,
    val Description: String="",
    val Status: String = "",
    val Theme: String = "",
    val Title: String = "",
): Serializable