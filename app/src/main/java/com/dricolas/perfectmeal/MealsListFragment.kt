package com.dricolas.perfectmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MealsListFragment : Fragment() {

    private val m_model : MealListViewModel by viewModels()

    private lateinit var m_adapter : RecyclerViewMealListAdapter

    private lateinit var m_view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_meals_list, container, false)

        m_adapter = RecyclerViewMealListAdapter(m_model.getMeals(), viewLifecycleOwner)

        val recyclerview = m_view?.findViewById<RecyclerView>(R.id.fragment_meal_list_recycler_view)

        // Setting the Adapter with the recyclerview
        recyclerview?.adapter = m_adapter

        // this creates a vertical layout Manager
        recyclerview?.layoutManager = LinearLayoutManager(view?.context)



        return m_view;
    }
}