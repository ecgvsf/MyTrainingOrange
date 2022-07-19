package com.example.mytrainingorange

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.mytrainingorange.databinding.ActivityMainBinding
import com.example.mytrainingorange.fragments.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var user : FirebaseUser

    companion object {
        lateinit var flag: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        flag = this

        database = Firebase.database.reference

        user = intent.extras?.get("User") as FirebaseUser

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            replaceFragment(HomeFragment(), "My Training Orange", R.id.home_page, R.id.nav_home, View.GONE)

        }



        binding.bottomNavigation.setOnItemSelectedListener {
            when (it) {
                R.id.home_page -> {
                    replaceFragment(HomeFragment(), "My Training Orange", R.id.home_page, R.id.nav_home, View.GONE)
                }
                R.id.diet_page -> {
                    replaceFragment(DietFragment(), "Diet", R.id.diet_page, R.id.nav_diet, View.GONE)
                }
                R.id.fridge_page -> {
                    replaceFragment(FridgeFragment(), "Your Fridge", R.id.fridge_page, R.id.nav_fridge, View.VISIBLE)
                }
                R.id.recipes_page -> {
                    replaceFragment(RecipesFragment(), "Recipes", R.id.recipes_page, R.id.nav_recipes, View.VISIBLE)
                }
                R.id.user_page -> {
                    replaceFragment(UserFragment(), "Settings User", R.id.user_page, R.id.nav_user, View.GONE)
                }
            }
            true
        }


        binding.orange.setOnClickListener {
            openMenu()
        }

        val settings: ImageView = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.settings)
        settings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment(), "My Training Orange", R.id.home_page, R.id.nav_home, View.GONE)
                }
                R.id.nav_diet -> {
                    replaceFragment(DietFragment(), "Diet", R.id.diet_page, R.id.nav_diet, View.GONE)
                }
                R.id.nav_fridge -> {
                    replaceFragment(FridgeFragment(), "Your Fridge", R.id.fridge_page, R.id.nav_fridge, View.VISIBLE)
                }
                R.id.nav_recipes -> {
                    replaceFragment(RecipesFragment(), "Recipes", R.id.recipes_page, R.id.nav_recipes, View.VISIBLE)
                }
                R.id.nav_user -> {
                    replaceFragment(UserFragment(), "Settings User", R.id.user_page, R.id.nav_user, View.GONE)
                }

                R.id.nav_pref -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                }
                R.id.nav_share -> {
                    val intent= Intent()
                    intent.action=Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
                    intent.type="text/plain"
                    startActivity(Intent.createChooser(intent,"Share To:"))
                }
                R.id.nav_log_out -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this, LogInActivity::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }



    fun openMenu(){
        binding.drawerLayout.open()
    }

    private fun replaceFragment(fragment: Fragment, name: String, idPage: Int, idNav: Int, visibility: Int) {
        val bundle = Bundle()
        bundle.putString("UserId", user.uid)
        fragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

        binding.titleText.text = name
        binding.bottomNavigation.setItemSelected(idPage, true)
        binding.navView.setCheckedItem(idNav)
        binding.searchIcon.visibility = visibility
    }

}

