package com.dricolas.perfectmeal

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dricolas.perfectmeal.rest.MealAPI
import com.dricolas.perfectmeal.rest.MealListItem
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import kotlinx.coroutines.launch


class MealListViewModel() : ViewModel() {

    private var api = MealAPI()

    private var mealsList : MutableLiveData<ArrayList<MealListItem>>? = null

    fun init(context : Context) : Boolean
    {
        val internetOk = checkForInternet(context)
        if(mealsList == null)
            mealsList = MutableLiveData<ArrayList<MealListItem>>()

        mealsList?.apply {
            value = ArrayList<MealListItem>()
            if(internetOk) {
                loadMeals(value)
            }
        }
        return internetOk
    }

    fun getMeals(): LiveData<ArrayList<MealListItem>>? {
        return mealsList
    }

    private fun loadMeals(value : ArrayList<MealListItem>? ) {
        // Do an asynchronous operation to fetch meals.
        for(i in 1..10) {
            viewModelScope.launch() {
                var lst = value

                for(j in 1..10) {
                    try {
                        lst?.add(api.getRandomRecipe())
                    }
                    catch (e: Throwable) {
                        Log.e("PARSE_ERROR", e.message.toString())
                    }
                }
                mealsList?.postValue(lst)
            }
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
