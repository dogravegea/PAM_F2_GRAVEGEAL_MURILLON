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

class RecyclerViewMealListAdapter(var meals : LiveData<ArrayList<MealListItem>>, var mealClickListener : OnMealClickListener) : RecyclerView.Adapter<RecyclerViewMealListAdapter.ViewHolder>(), Filterable {

    private lateinit var m_view : View

    private var mealsFilterList = ArrayList<MealListItem>()

    init {
        majFilterList()
    }

    fun majFilterList(){
        mealsFilterList = meals.value!!
    }

    fun CustomNotifyDataSetChanged(){
        majFilterList()
        this.notifyDataSetChanged()
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        m_view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_list_item, parent, false)

        return ViewHolder(m_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val MealListItem = mealsFilterList[position]

        // Sets the image to the imageview from our itemHolder class
        m_view.let { Glide.with(it.context).load(MealListItem.strMealThumb).into(holder.mealThumbnail) }
        holder.mealName.text = MealListItem.strMeal?.subSequence(0, Math.min(MealListItem.strMeal.length, 45))
        holder.mealCategory.text = MealListItem.strCategory

        holder.mealDetailCard.setOnClickListener {
            mealClickListener.OnMealClick(MealListItem)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    mealsFilterList = meals.value!!
                } else {
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

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val mealThumbnail: ImageView = itemView.findViewById(R.id.meal_list_item_thumbnail)
        val mealName: TextView = itemView.findViewById(R.id.meal_list_item_name)
        val mealCategory: TextView = itemView.findViewById(R.id.meal_list_item_category)
        val mealDetailCard: CardView = itemView.findViewById(R.id.meal_list_item)
    }
}