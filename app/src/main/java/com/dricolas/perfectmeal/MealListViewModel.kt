package com.dricolas.perfectmeal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MealListViewModel : ViewModel() {

    private var api = MealAPIEndpoint()
    private val mealsList: MutableLiveData<ArrayList<MealListItem>> by lazy {
        MutableLiveData<ArrayList<MealListItem>>().apply {

            value = ArrayList<MealListItem>()

            val job = viewModelScope.launch {
                value?.add(api.getRandomRecipe())
            }

//            value?.add(MealListItem("52879","Chicken Parmentier",null, "Chicken","French",
//                "For the topping, boil the potatoes in salted water until tender. Drain and push through a potato ricer, or mash thoroughly. Stir in the butter, cream and egg yolks. Season and set aside.\r\nFor the filling, melt the butter in a large pan. Add the shallots, carrots and celery and gently fry until soft, then add the garlic. Pour in the wine and cook for 1 minute. Stir in the tomato pur\u00e9e, chopped tomatoes and stock and cook for 10\u201315 minutes, until thickened. Add the shredded chicken, olives and parsley. Season to taste with salt and pepper.\r\nPreheat the oven to 180C/160C Fan/Gas 4.\r\nPut the filling in a 20x30cm/8x12in ovenproof dish and top with the mashed potato. Grate over the Gruy\u00e8re. Bake for 30\u201335 minutes, until piping hot and the potato is golden-brown.",
//                "https://www.themealdb.com/images/media/meals/uwvxpv1511557015.jpg",
//                "Meat,Dairy",
//                "Potatoes",
//                "Butter",
//                "Double Cream",
//                "Egg Yolks",
//                "Butter",
//                "Shallots",
//                "Carrots",
//                "Celery",
//                "Garlic Clove",
//                "White Wine",
//                "Tomato Puree",
//                "Tinned Tomatos",
//                "Chicken Stock",
//                "Chicken",
//                "Black Olives",
//                "Parsley",
//                "Gruyere cheese",
//                "",
//                "",
//                "",
//                "1.5kg",
//                "30g",
//                "5 tblsp ",
//                "2",
//                "30g",
//                "7",
//                "3 chopped",
//                "2 sticks",
//                "1 finely chopped ",
//                "4 tbsp",
//                "1 tbls",
//                "400g",
//                "350ml",
//                "600g",
//                "16",
//                "2 tbs",
//                "50g",
//                "",
//                "",
//                ""))
            }
        }


    fun getMeals(): LiveData<ArrayList<MealListItem>> {
        return mealsList
    }

    //private fun loadUsers() {
    //    // Do an asynchronous operation to fetch users.
    //}
}
