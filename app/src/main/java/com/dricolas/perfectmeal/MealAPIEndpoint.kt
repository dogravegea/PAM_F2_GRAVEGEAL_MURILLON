package com.dricolas.perfectmeal

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

class MealAPIEndpoint {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
    }

    private val api_base_url = "https://www.themealdb.com/api/json/v1/1/"

    suspend fun getRandomRecipe() : MealListItem {
        val res = client.get<MealList>(api_base_url + "random.php")
        return res.meals[0]
    }
}