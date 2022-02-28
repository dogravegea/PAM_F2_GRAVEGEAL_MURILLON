package com.dricolas.perfectmeal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dricolas.perfectmeal.rest.MealListItem
import java.util.*
import kotlin.collections.ArrayList

//*** Adapter for the recyclerView for the meal list
class RecyclerViewMealListAdapter(var meals : LiveData<ArrayList<MealListItem>>, var mealClickListener : OnMealClickListener) : RecyclerView.Adapter<RecyclerViewMealListAdapter.ViewHolder>(), Filterable {

    //*** Current view
    private lateinit var m_view : View

    //*** Internal filtered list, this list is displayed in the recyclerview, not the complete list
    private var mealsFilterList = ArrayList<MealListItem>()

    //*** Initialising the filtered list to the full list
    init {
        majFilterList()
    }

    //*** Initialising the filtered list to the full list
    private fun majFilterList(){
        mealsFilterList = meals.value!!
    }

    //*** Update the filtered list and notify
    fun customNotifyDataSetChanged(){
        majFilterList()
        this.notifyDataSetChanged()
    }

    //*** Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        m_view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_list_item, parent, false)

        return ViewHolder(m_view)
    }

    //*** Bind the data to each view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //*** Meal to display
        val mealListItem = mealsFilterList[position]

        //*** Sets the image to the imageview from our itemHolder class
        m_view.let { Glide.with(it.context).load(mealListItem.strMealThumb).into(holder.mealThumbnail) }
        holder.mealName.text = mealListItem.strMeal
        holder.mealCategory.text = mealListItem.strCategory

        //*** Handling the click to access the detail fragment
        holder.mealDetailCard.setOnClickListener {
            mealClickListener.OnMealClick(mealListItem)
        }
    }

    //*** Update the filtered list
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                //*** If the search text is empty,display the full list
                if (charSearch.isEmpty()) {
                    mealsFilterList = meals.value!!
                }
                else {
                    //*** Else, display the element matching for the noun or the category
                    val resultList = ArrayList<MealListItem>()
                    for (meal in meals.value!!) {
                        if (   ((meal.strMeal)!!.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)))
                            || ((meal.strCategory)!!.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)))) {
                            resultList.add(meal)
                        }
                    }
                    mealsFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mealsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mealsFilterList = results?.values as ArrayList<MealListItem>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mealsFilterList.size
    }

    //*** Getting the fields
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val mealThumbnail: ImageView = itemView.findViewById(R.id.meal_list_item_thumbnail)
        val mealName: TextView = itemView.findViewById(R.id.meal_list_item_name)
        val mealCategory: TextView = itemView.findViewById(R.id.meal_list_item_category)
        val mealDetailCard: CardView = itemView.findViewById(R.id.meal_list_item)
    }
}