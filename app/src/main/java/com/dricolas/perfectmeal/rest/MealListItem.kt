package com.dricolas.perfectmeal.rest

import kotlinx.serialization.Serializable

@Serializable
data class MealListItem(
    val idMeal: String?,
    val strMeal : String?,
    val strDrinkAlternate: String?,
    val strCategory : String?,
    val strArea : String?,
    val strInstructions : String?,
    val strMealThumb :String?,
    val strTags: String?,
    val ingredients : ArrayList<Ingredient>
)

@Serializable
data class Ingredient(val name : String, val quantity : String)
