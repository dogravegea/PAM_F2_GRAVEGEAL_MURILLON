package com.dricolas.perfectmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dricolas.perfectmeal.rest.MealListItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//*** Fragment to display the meal list
class MealsListFragment : Fragment() {

    //*** Current view
    private lateinit var m_view : View
    //*** View model holding the meals
    private val m_model : MealListViewModel by viewModels()
    //*** Adapter from the recyclerView containing the meals
    private lateinit var m_adapter : RecyclerViewMealListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //*** Access to the view
        m_view = inflater.inflate(R.layout.fragment_meals_list, container, false)

        //*** Getting the fields
        val recyclerview = m_view.findViewById<RecyclerView>(R.id.fragment_meal_list_recycler_view)
        val searchBar = m_view.findViewById<SearchView>(R.id.fragment_meal_list_search_view)
        val progressBar = m_view.findViewById<ProgressBar>(R.id.fragment_meal_list_progressBar)
        val errorContainer = m_view.findViewById<ConstraintLayout>(R.id.fragment_meal_list_error_container)
        val retryButton = m_view.findViewById<Button>(R.id.fragment_meal_list_btnRetry)

        //*** Display a loading bar during the asynchronous API call
        retryButton.setOnClickListener {
            updateInterfaceLoading(progressBar, errorContainer)
        }
        if(m_model.getMeals() == null) {
            updateInterfaceLoading(progressBar, errorContainer)
        }

        //*** Create the meal list adapter for the recycler view
        m_adapter = RecyclerViewMealListAdapter(m_model.getMeals()!!) { meal ->
            //*** Callback for the navigation to the detail fragment when clicking a meal
            navigateToMealDetails(meal)
        }

        //*** Observer on the livedata to update the recyclerview adapter on each change
        m_model.getMeals()!!.observe(viewLifecycleOwner, { meals ->
            m_adapter.customNotifyDataSetChanged()

            if(meals.size != 0)
                progressBar.visibility = View.GONE
        })

        //*** Setting the Adapter with the recyclerview
        recyclerview?.adapter = m_adapter

        //*** This creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)

        //*** Searching in the list
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

    //*** Navigate to the details fragment
    private fun navigateToMealDetails(meal : MealListItem)
    {
        val bundle = Bundle()
        bundle.putString("selected_meal", Json.encodeToString(meal))
        findNavController().navigate(R.id.action_mealsListFragment_to_mealDetailsFragment, bundle)
    }

    //*** Display / Hide the progress bar and the error message if the is no internet connexion
    private fun updateInterfaceLoading(progressBar : ProgressBar, ErrorContainer: ConstraintLayout)
    {
        if(m_model.init(m_view.context))
        {
            progressBar.visibility = View.VISIBLE
            ErrorContainer.visibility = View.GONE
        }
        else
        {
            progressBar.visibility = View.GONE
            ErrorContainer.visibility = View.VISIBLE
        }
    }
}