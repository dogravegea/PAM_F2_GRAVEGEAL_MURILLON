package com.dricolas.perfectmeal

import com.dricolas.perfectmeal.rest.MealListItem

//*** Listener to handle a click on a meal
fun interface OnMealClickListener {
    fun OnMealClick(meal : MealListItem)
}