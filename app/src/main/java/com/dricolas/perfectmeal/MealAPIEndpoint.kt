package com.dricolas.perfectmeal

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.JSONObject

class MealAPIEndpoint {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    private val api_base_url = "www.themealdb.com/api/json/v1/1/";

    public suspend fun getRandomRecipe() : MealListItem {
        var ret = MealListItem(-1, "toto", "toto", "123")

        var meal = client.get<String>("www.themealdb.com/api/json/v1/1/random.php")
        var mealJSON = JSONObject(meal)

        return ret
    }
}