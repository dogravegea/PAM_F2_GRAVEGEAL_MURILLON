package com.dricolas.perfectmeal

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.lang.Integer.min


class RecyclerViewMealListAdapter(var meals : LiveData<ArrayList<MealListItem>>, var viewLifecycleOwner : LifecycleOwner) : RecyclerView.Adapter<RecyclerViewMealListAdapter.ViewHolder>() {

    private var m_view : View? = null

    init {
        meals.observe(viewLifecycleOwner, Observer<ArrayList<MealListItem>>{ meals ->
            notifyDataSetChanged()
        })
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        m_view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_list_item, parent, false)

        return ViewHolder(m_view!!)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val MealListItem = meals.value?.get(position)

        // sets the image to the imageview from our itemHolder class
        m_view?.let { Glide.with(it.context).load(MealListItem?.strMealThumb).into(holder.mealThumbnail) }
        holder.mealName.text = MealListItem?.strMeal?.subSequence(0, Math.min(MealListItem?.strMeal.length - 1, 30))
        holder.mealCategory.text = MealListItem?.strCategory?.subSequence(0, Math.min(MealListItem?.strCategory.length - 1, 20))
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return meals.value?.size!!
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val mealThumbnail: ImageView = itemView.findViewById(R.id.meal_list_item_thumbnail)
        val mealName: TextView = itemView.findViewById(R.id.meal_list_item_name)
        val mealCategory: TextView = itemView.findViewById(R.id.meal_list_item_category)
        val mealDetailButton: TextView = itemView.findViewById(R.id.meal_list_item_button_details)
    }
}