package com.dricolas.perfectmeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MealListViewModel : ViewModel() {

    private var api = MealAPIEndpoint();
    private val mealsList: MutableLiveData<ArrayList<MealListItem>> by lazy {
        MutableLiveData<ArrayList<MealListItem>>().apply {

            value = ArrayList<MealListItem>()
            viewModelScope.launch {
                value?.add(api.getRandomRecipe())
            }
            //value?.add(MealListItem(52866, "Squash linguine", "Vegetarian", "https://www.themealdb.com/images/media/meals/wxswxy1511452625.jpg"))
        }
    }

    fun getMeals(): LiveData<ArrayList<MealListItem>> {
        return mealsList
    }

    //private fun loadUsers() {
    //    // Do an asynchronous operation to fetch users.
    //}
}
