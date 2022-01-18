package com.dricolas.perfectmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MealsListFragment : Fragment() {

    private val m_model : MealListViewModel by viewModels()

    private lateinit var m_adapter : RecyclerViewMealListAdapter

    private lateinit var m_view : View

    private lateinit var navHostFragment : NavHostFragment
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_meals_list, container, false)

        navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        m_adapter = RecyclerViewMealListAdapter(m_model.getMeals()) { meal ->
            val bundle = Bundle()
            bundle.putString("selected_meal", Json.encodeToString(meal))

            navController.navigate(R.id.action_mealsListFragment_to_mealDetailsFragment, bundle)
        }

        m_model.getMeals().observe(viewLifecycleOwner, { meals ->
            m_adapter.notifyDataSetChanged()
        })

        val recyclerview = m_view.findViewById<RecyclerView>(R.id.fragment_meal_list_recycler_view)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = m_adapter

        // this creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)

        return m_view
    }
}