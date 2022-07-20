package com.example.mytrainingorange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mytrainingorange.adapter.VPAdapter
import com.example.mytrainingorange.databinding.ActivityMealBinding
import com.example.mytrainingorange.fragments.TabFavouritesFragment
import com.example.mytrainingorange.fragments.TabPersonalFragment
import com.example.mytrainingorange.fragments.TabRecipesFragment

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tabs.setupWithViewPager(binding.pager)
        val vpAdapter = VPAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        vpAdapter.addFragment(TabRecipesFragment(), "Recipes")
        vpAdapter.addFragment(TabPersonalFragment(), "Personal")
        vpAdapter.addFragment(TabFavouritesFragment(), "Favourites")
        binding.pager.adapter = vpAdapter

        binding.backButton.setOnClickListener{
            finish()
        }

    }
}