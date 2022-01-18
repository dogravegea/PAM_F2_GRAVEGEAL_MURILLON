package com.dricolas.perfectmeal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dricolas.perfectmeal.rest.MealListItem

class RecyclerViewMealListAdapter(var meals : LiveData<ArrayList<MealListItem>>, var mealClickListener : OnMealClickListener) : RecyclerView.Adapter<RecyclerViewMealListAdapter.ViewHolder>() {

    private lateinit var m_view : View

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        m_view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_list_item, parent, false)

        return ViewHolder(m_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val MealListItem = meals.value?.get(position)

        // Sets the image to the imageview from our itemHolder class
        m_view.let { Glide.with(it.context).load(MealListItem?.strMealThumb).into(holder.mealThumbnail) }
        holder.mealName.text = MealListItem?.strMeal?.subSequence(0, Math.min(MealListItem.strMeal.length, 30))
        holder.mealCategory.text = MealListItem?.strCategory

        holder.mealDetailButton.setOnClickListener {
            mealClickListener.OnMealClick(MealListItem!!)
        }
    }

    override fun getItemCount(): Int {
        return meals.value?.size!!
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val mealThumbnail: ImageView = itemView.findViewById(R.id.meal_list_item_thumbnail)
        val mealName: TextView = itemView.findViewById(R.id.meal_list_item_name)
        val mealCategory: TextView = itemView.findViewById(R.id.meal_list_item_category)
        val mealDetailButton: TextView = itemView.findViewById(R.id.meal_list_item_button_details)
    }
}