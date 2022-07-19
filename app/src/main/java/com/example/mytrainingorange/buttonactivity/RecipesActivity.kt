package com.example.mytrainingorange.buttonactivity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.mytrainingorange.R
import com.example.mytrainingorange.adapter.PAdapter
import com.example.mytrainingorange.adapter.PAdapterMenu
import com.example.mytrainingorange.adapter.PVerticalAdapter
import com.example.mytrainingorange.model.Recipe

class RecipesActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView

    private var title: String = ""
    private var description: String = ""
    private var image: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        imageView = findViewById(R.id.image)
        titleView = findViewById(R.id.title)
        descriptionView = findViewById(R.id.description)

        getData()
        setData()
    }

    private fun getData() {
        if(intent.hasExtra("title") &&
            intent.hasExtra("description") &&
            intent.hasExtra("image")) {

            title = intent.getStringExtra("title").toString()
            description = intent.getStringExtra("description").toString()
            image = intent.getIntExtra("image", 1)

        }
        else
            Toast.makeText(this, "Nessun dato trovato", Toast.LENGTH_SHORT).show()
    }

    private fun setData(){
        titleView.text = title
        descriptionView.text = description
        imageView.setImageResource(image)
    }
}