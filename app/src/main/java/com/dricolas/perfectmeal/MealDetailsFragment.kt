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

class MealDetailsFragment : Fragment() {

    private lateinit var m_view : View
    private lateinit var m_meal : MealListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m_meal = Json.decodeFromString<MealListItem>(arguments?.getString("selected_meal")!!)
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

        m_view.let { Glide.with(it.context).load(m_meal.strMealThumb).into(image) }
        name.text = m_meal.strMeal
        category.text = m_meal.strCategory

        return m_view
    }
}