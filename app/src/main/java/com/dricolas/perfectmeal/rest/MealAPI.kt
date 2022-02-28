package com.dricolas.perfectmeal.rest

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

//*** Access to theMealDb API
class MealAPI {
    //*** Http client
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

    //*** API base url
    private val api_base_url = "https://www.themealdb.com/api/json/v1/1/"

    //*** Retrieve a random recipe from the API
    suspend fun getRandomRecipe() : MealListItem {
        val mealListString = client.get<String>(api_base_url + "random.php")
        var mealListJsonElement = Json.decodeFromString<JsonElement>(mealListString)
        val meals = processMeals(mealListJsonElement)
        return meals
    }

    //*** Convert a meal retrieved from the API to our data structures
    fun processMeals(m : JsonElement) : MealListItem
    {
        //*** Convert to a JSON object
        var jsonMeal  = (m.jsonObject["meals"] as JsonArray)[0] as JsonObject
        var ingredients = ArrayList<Ingredient>()

        //*** Process ingredients and quantities
        var i = 1
        var ingredient = jsonMeal["strIngredient$i"]!!.jsonPrimitive.content
        var ingredient_measure = jsonMeal["strMeasure$i"]!!.jsonPrimitive.content
        while(ingredient != "" && i <= 20) {
            ingredients.add(Ingredient(ingredient, ingredient_measure))
            i++
            ingredient = jsonMeal["strIngredient$i"]!!.jsonPrimitive.content
            ingredient_measure = jsonMeal["strMeasure$i"]!!.jsonPrimitive.content
        }

        //*** All other attributes
        var meal = MealListItem(
            jsonMeal.jsonObject["idMeal"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strMeal"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strDrinkAlternate"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strCategory"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strArea"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strInstructions"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strMealThumb"]!!.jsonPrimitive.content,
            jsonMeal.jsonObject["strTags"]!!.jsonPrimitive.content,
            ingredients
        )

        return meal;
    }
}