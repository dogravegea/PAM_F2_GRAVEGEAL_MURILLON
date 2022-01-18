package com.dricolas.perfectmeal

import com.dricolas.perfectmeal.rest.MealListItem

fun interface OnMealClickListener {
    fun OnMealClick(meal : MealListItem)
}