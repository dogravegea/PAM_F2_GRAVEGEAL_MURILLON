package com.dricolas.perfectmeal

import kotlinx.serialization.Serializable

@Serializable
data class MealListItem(
    val ID: Int,
    val name : String,
    val category : String,
    val image_url :String)
