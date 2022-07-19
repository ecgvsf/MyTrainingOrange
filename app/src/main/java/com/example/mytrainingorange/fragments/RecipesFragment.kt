package com.example.mytrainingorange.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mytrainingorange.R
import com.example.mytrainingorange.adapter.PAdapter
import com.example.mytrainingorange.adapter.PAdapterMenu
import com.example.mytrainingorange.adapter.PVerticalAdapter
import com.example.mytrainingorange.adapter.RecycleAdapter
import com.example.mytrainingorange.buttonactivity.RecipesActivity
import com.example.mytrainingorange.model.Recipe
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class RecipesFragment : Fragment() {

    private lateinit var viewPagerRecipe: ViewPager
    private lateinit var viewPagerMenu: ViewPager
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: PVerticalAdapter
    private lateinit var adapterMenu: PAdapterMenu
    private lateinit var adapterRecipe: PAdapter
    private lateinit var recipes: ArrayList<Recipe>
    private lateinit var menuRecipes: ArrayList<Recipe>
    private lateinit var menu: ArrayList<ViewPager>
    private lateinit var database : DatabaseReference

    private lateinit var recyclerView: RecyclerView

    /*val searchListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

        }

        override fun onCancelled(error: DatabaseError) {

        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_recipes,
            container, false
        )

        database = Firebase.database.reference

        recipes = ArrayList()
        recipes.add(Recipe(R.drawable.risotto_ripieno, "risotto_ripieno", "Description"))
        recipes.add(Recipe(R.drawable.friselle, "friselle", "Description"))
        recipes.add(Recipe(R.drawable.avocado_toast, "avocado_toast", "Description"))
        recipes.add(Recipe(R.drawable.coccoretti, "coccoretti", "Description"))
        recipes.add(Recipe(R.drawable.gamberi_lime, "gamberi_lime", "Description"))
        recipes.add(Recipe(R.drawable.ricetta_uova, "ricetta_uova", "Description"))

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = RecycleAdapter(requireContext(), recipes)
        recyclerView.scrollToPosition((Int.MAX_VALUE/2)-(Int.MAX_VALUE/2)%recipes.size)

        recyclerView.addOnScrollListener(
            CircularScrollListener()
        )


        //database.child("food").startAt("pollo","name").addValueEventListener(searchListener)


        /*
        adapterRecipe = PAdapter(recipes, requireContext())

        viewPagerRecipe = ViewPager(requireContext())
        viewPagerRecipe.adapter = adapterRecipe
        viewPagerRecipe.setPadding(20, 0, 20, 0)


        menuRecipes = ArrayList()
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))
        menuRecipes.add(Recipe(R.drawable.icon_background, "Title", "Description"))

        adapterMenu = PAdapterMenu(menuRecipes, requireContext())

        viewPagerMenu = ViewPager(requireContext())
        viewPagerMenu.adapter = adapterMenu
        viewPagerMenu.setPadding(20, 0, 20, 0)

        menu = ArrayList()
        menu.add(viewPagerRecipe)
        menu.add(viewPagerMenu)

        adapter = PVerticalAdapter(menu, requireContext())

        viewPager = view!!.findViewById(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.setPadding(20, 0, 20, 0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
            override fun onPageSelected(position: Int) {
                // Your code
            }

            // This method will be invoked when the current page is scrolled, either as part of
            // a programmatically initiated smooth scroll or a user initiated touch scroll.
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Your code
            }

            // Called when the scroll state changes. Useful for discovering when the user begins
            // dragging, when the pager is automatically settling to the current page,
            // or when it is fully stopped/idle.
            override fun onPageScrollStateChanged(state: Int) {
                // Your code

            }
        })

        viewPagerMenu.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
            override fun onPageSelected(position: Int) {
                // Your code
            }

            // This method will be invoked when the current page is scrolled, either as part of
            // a programmatically initiated smooth scroll or a user initiated touch scroll.
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Your code
            }

            // Called when the scroll state changes. Useful for discovering when the user begins
            // dragging, when the pager is automatically settling to the current page,
            // or when it is fully stopped/idle.
            override fun onPageScrollStateChanged(state: Int) {
                // Your code

            }
        })

        viewPagerRecipe.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
            override fun onPageSelected(position: Int) {
                // Your code
            }

            // This method will be invoked when the current page is scrolled, either as part of
            // a programmatically initiated smooth scroll or a user initiated touch scroll.
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Your code
            }

            // Called when the scroll state changes. Useful for discovering when the user begins
            // dragging, when the pager is automatically settling to the current page,
            // or when it is fully stopped/idle.
            override fun onPageScrollStateChanged(state: Int) {
                // Your code

            }
        })
         */
        return view
    }

    fun inizia() {
        Log.w("inizia","seso")
        startActivity(Intent(context, RecipesActivity::class.java))
    }


    inner class CircularScrollListener: RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItemVisible: Int = layoutManager.findFirstVisibleItemPosition()
            if (firstItemVisible != 1 && (firstItemVisible % recipes.size == 1)) {
                layoutManager.scrollToPosition(1)
            } else if (firstItemVisible != 1 && firstItemVisible > recipes.size && (firstItemVisible % recipes.size > 1)) {
                layoutManager.scrollToPosition(firstItemVisible % recipes.size)
            } else if (firstItemVisible == 0) {
                layoutManager.scrollToPositionWithOffset(recipes.size, - recyclerView.computeHorizontalScrollOffset())
            }
        }
    }
}