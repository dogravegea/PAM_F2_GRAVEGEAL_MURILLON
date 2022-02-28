package com.dricolas.perfectmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dricolas.perfectmeal.rest.MealListItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

//*** Fragment for the details of a meal
class MealDetailsFragment : Fragment() {
    //*** Current view
    private lateinit var m_view : View
    //*** Meal to display in the details view
    private lateinit var m_meal : MealListItem
    //*** JSON parser
    private lateinit var json : JsonElement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //*** Get the meal to display from parameters
        json = Json.parseToJsonElement(arguments?.getString("selected_meal")!!)
        m_meal = Json.decodeFromString(arguments?.getString("selected_meal")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //*** Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_meal_details, container, false)

        //*** Get the UI fields
        val image : ImageView = m_view.findViewById(R.id.meal_details_thumbnail)
        val name : TextView = m_view.findViewById(R.id.meal_details_name)
        val category : TextView = m_view.findViewById(R.id.meal_details_category)
        val ingredients : TextView = m_view.findViewById(R.id.meal_details_ingredients)
        val instructions : TextView = m_view.findViewById(R.id.meal_details_instructions)

        //*** Creating the list of ingredients to display in a string
        var ingredient_list = ""
        for(ingredient in m_meal.ingredients) {
            ingredient_list += "• " + ingredient.name + " - " + ingredient.quantity + "\n"
        }

        //*** Filling fields
        //    Loading thumbnail from url
        m_view.let { Glide.with(it.context).load(m_meal.strMealThumb).into(image) }
        //    Other fields
        name.text = m_meal.strMeal
        category.text = m_meal.strCategory
        ingredients.text = ingredient_list
        instructions.text = m_meal.strInstructions

        return m_view
    }
}