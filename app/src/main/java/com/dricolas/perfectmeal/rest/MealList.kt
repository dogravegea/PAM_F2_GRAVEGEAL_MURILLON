package com.dricolas.perfectmeal.rest

import kotlinx.serialization.Serializable

@Serializable
data class MealList(
    val meals: ArrayList<MealListItem>
)
