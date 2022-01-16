package com.dricolas.perfectmeal

import kotlinx.serialization.Serializable

@Serializable
data class MealList(
    val meals: ArrayList<MealListItem>
)
