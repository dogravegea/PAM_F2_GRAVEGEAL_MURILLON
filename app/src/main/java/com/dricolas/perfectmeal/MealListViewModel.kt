package com.dricolas.perfectmeal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch


class MealListViewModel : ViewModel() {

    private var api = MealAPIEndpoint()
    private val mealsList: MutableLiveData<ArrayList<MealListItem>> = MutableLiveData<ArrayList<MealListItem>>().apply {
            if(value == null) {
                value = ArrayList<MealListItem>()
                loadMeals(value)
            }
    }


    fun getMeals(): LiveData<ArrayList<MealListItem>> {
        return mealsList
    }

    private fun loadMeals(value : ArrayList<MealListItem>? ) {
        // Do an asynchronous operation to fetch meals.

        for(i in 1..10) {
            val job = viewModelScope.launch() {
                var lst : ArrayList<MealListItem> =  ArrayList<MealListItem>()

                if(value != null) {
                    lst = value
                }

                for(i in 1..10) {
                    try {
                        lst.add(api.getRandomRecipe())
                    } catch (e: Throwable) {
                        Log.e("PARSE_ERROR", "An error occurred while parsing the json response.")
                    }
                }
                mealsList.postValue(lst)
            }
        }
    }
}
