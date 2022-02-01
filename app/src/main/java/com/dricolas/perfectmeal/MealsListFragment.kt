package com.dricolas.perfectmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dricolas.perfectmeal.rest.MealListItem
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MealsListFragment : Fragment() {

    private lateinit var m_view : View

    private val m_model : MealListViewModel by viewModels()

    private lateinit var m_adapter : RecyclerViewMealListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        m_view = inflater.inflate(R.layout.fragment_meals_list, container, false)

        val recyclerview = m_view.findViewById<RecyclerView>(R.id.fragment_meal_list_recycler_view)

        val searchBar = m_view.findViewById<SearchView>(R.id.fragment_meal_list_search_view)

        val progressBar = m_view.findViewById<ProgressBar>(R.id.fragment_meal_list_progressBar)

        m_adapter = RecyclerViewMealListAdapter(m_model.getMeals()) { meal ->
            navigateToMealDetails(meal)
        }

        m_model.getMeals().observe(viewLifecycleOwner, { meals ->
            m_adapter.notifyDataSetChanged()

            if(meals.size == 0)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = m_adapter

        // this creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)

        // searching in the list
        searchBar?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                m_adapter.filter.filter(newText)
                return false
            }
        })

        return m_view
    }

    fun navigateToMealDetails(meal : MealListItem)
    {
        val bundle = Bundle()
        bundle.putString("selected_meal", Json.encodeToString(meal))
        findNavController().navigate(R.id.action_mealsListFragment_to_mealDetailsFragment, bundle)
    }
}