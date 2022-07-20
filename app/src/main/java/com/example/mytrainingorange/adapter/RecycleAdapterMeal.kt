package com.example.mytrainingorange.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrainingorange.adapter.RecycleAdapterMeal.RecyclerHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.mytrainingorange.R
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.mytrainingorange.buttonactivity.RecipesActivity
import android.widget.TextView
import android.widget.LinearLayout
import com.example.mytrainingorange.model.Recipe
import java.util.ArrayList

class RecycleAdapterMeal(var context: Context, var recipe: ArrayList<Recipe>) :
    RecyclerView.Adapter<RecyclerHolder>() {

    var noOfItem : Int = recipe.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.meal, parent, false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val actPpos = position % recipe.size

        holder.title.text = recipe[actPpos].title
        holder.description.text = recipe[actPpos].desc
        holder.kcal.text = recipe[actPpos].kcal


        holder.image.setOnClickListener {
            TODO() // add meal
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        val kcal: TextView
        var image: ImageView

        init {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            image = itemView.findViewById(R.id.add_button)
            kcal = itemView.findViewById(R.id.kcal)
        }
    }
}