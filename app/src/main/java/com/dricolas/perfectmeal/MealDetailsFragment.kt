package com.dricolas.perfectmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dricolas.perfectmeal.rest.MealListItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class MealDetailsFragment : Fragment() {

    private lateinit var m_view : View
    private lateinit var m_meal : MealListItem
    private lateinit var json : JsonElement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        json = Json.parseToJsonElement(arguments?.getString("selected_meal")!!)
        m_meal = Json.decodeFromString(arguments?.getString("selected_meal")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_meal_details, container, false)

        val image : ImageView = m_view.findViewById(R.id.meal_details_thumbnail)
        val name : TextView = m_view.findViewById(R.id.meal_details_name)
        val category : TextView = m_view.findViewById(R.id.meal_details_category)
        val ingredients : TextView = m_view.findViewById(R.id.meal_details_ingredients)
        val instructions : TextView = m_view.findViewById(R.id.meal_details_instructions)

        var ingredient_list = ""
        for(ingredient in m_meal.ingredients)
        {
            ingredient_list += "• " + ingredient.name + " - " + ingredient.quantity + "\n"
        }

        m_view.let { Glide.with(it.context).load(m_meal.strMealThumb).into(image) }
        name.text = m_meal.strMeal
        category.text = m_meal.strCategory
        ingredients.text = ingredient_list
        instructions.text = m_meal.strInstructions

        return m_view
    }
}