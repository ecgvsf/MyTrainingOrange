package com.example.mytrainingorange.fragments

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import bytesEqualTo
import com.example.mytrainingorange.MainActivity
import com.example.mytrainingorange.MealActivity
import com.example.mytrainingorange.R
import com.example.mytrainingorange.buttonactivity.RecipesActivity
import com.example.mytrainingorange.databinding.FragmentDietBinding
import com.example.mytrainingorange.listener.CustomAnimationDrawableNew
import com.example.mytrainingorange.model.Diet
import com.example.mytrainingorange.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt


class DietFragment : Fragment() {
    private var _binding: FragmentDietBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var database : DatabaseReference
    private lateinit var user: FirebaseAuth

    private lateinit var glasses : ArrayList<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietBinding.inflate(inflater, container, false)

        database = Firebase.database.reference
        user = Firebase.auth


        refreshData()

        binding.refreshLayout.setOnRefreshListener{
            refreshData()
            binding.refreshLayout.isRefreshing = false
        }

        //bars[0].progress =

        binding.waterAddButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_down))
        binding.waterAddButton.setOnClickListener{ water() }
        binding.mealAddButton.setOnClickListener{ mealActivity() }
        binding.glass1.setOnClickListener{
            binding.glass1.setBackgroundResource(R.drawable.filling)
            val fillAnimation : AnimationDrawable = binding.glass1.background as AnimationDrawable
            fillAnimation.start()
        }

        glasses = ArrayList()
        glasses.add(binding.glass1)
        glasses.add(binding.glass2)
        glasses.add(binding.glass3)
        glasses.add(binding.glass4)
        glasses.add(binding.glass5)
        glasses.add(binding.glass6)
        glasses.add(binding.glass7)
        for (i in glasses.indices){
            glasses[i].setOnClickListener(WaterAnimation(i))
        }

        /*
        binding.layout.setOnTouchListener(object : OnSwipeTouchListener(this@DietFragment.context) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                (activity as MainActivity?)!!.openMenu()
            }
        })
         */

        return binding.root
    }

    private fun refreshData() {
        database.child("users").child(user.uid!!).get().addOnSuccessListener {
            val userData = it.getValue<User>()
            if (userData != null) {
                var calToHit = calculateKcal(userData.weight!!, userData.sex!!)
                var currentData = userData.cal!!
                binding.kcalProgressIndicator.text = "${currentData.roundToInt()}/${calToHit.roundToInt()} Kcal"
                binding.kcalProgressBar.progress = (currentData / calToHit * 100).toInt()

                currentData = userData.water!!
                var dataToHit = 2.5f
                binding.waterProgressIndicator.text = "${(currentData * 100.0).roundToInt() / 100.0}/${(dataToHit * 100.0).roundToInt() / 100.0} L"
                binding.waterProgressBar.progress = (currentData / dataToHit * 100).toInt()

                currentData = userData.carbo!!
                dataToHit = (((userData.diet as Diet).maxCarbo!! + (userData.diet).minCarbo!!) / 2) / 100 * calToHit / 4
                binding.carboProgressIndicator.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"
                binding.carboProgressBar.progress = (currentData / dataToHit * 100).toInt()

                currentData = userData.fats!!
                dataToHit = (((userData.diet).maxFats!! + (userData.diet).minFats!!) / 2) / 100 * calToHit / 9
                binding.fatsProgressIndicator.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"
                binding.lipidsProgressBar.progress = (currentData / dataToHit * 100).toInt()

                currentData = userData.proteins!!
                dataToHit = (((userData.diet).maxProteins!! + (userData.diet).minProteins!!) / 2) / 100 * calToHit / 4
                binding.proteinsProgressIndicator.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"
                binding.proteinsProgressBar.progress = (currentData / dataToHit * 100).toInt()
            }
        }
    }

    inner class WaterAnimation(private var pos: Int): View.OnClickListener{
        override fun onClick(p0: View?) {

            /*
            var fillingAnimation = glasses[pos].background as AnimationDrawable
            glasses[pos].background = fillingAnimation
            fillingAnimation.start()

             */

            var stato = glasses[pos].background as AnimationDrawable

            // Pass our animation drawable to our custom drawable class
            val cad = object : CustomAnimationDrawableNew(
                stato
            ) {
                override fun onAnimationStart() {

                }

                override fun onAnimationFinish() {
                    if (glasses[pos].background.bytesEqualTo(context?.getDrawable(R.drawable.filling))){
                        glasses[pos].background = context?.getDrawable(R.drawable.emptying) as AnimationDrawable
                    } else {
                        glasses[pos].background = context?.getDrawable(R.drawable.filling) as AnimationDrawable
                    }
                }
            }
            glasses[pos].background = cad
            cad.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mealActivity() {
        val intent = Intent (activity, MealActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun water() {
        var v : Int = if(binding.waterList.visibility == View.GONE)
            View.VISIBLE
        else
            View.GONE
        TransitionManager.beginDelayedTransition(binding.waterLayout, AutoTransition())
        binding.waterList.visibility = v
        var i : Int = if(binding.waterList.visibility != View.GONE)
            R.drawable.ic_up
        else
            R.drawable.ic_down
        binding.waterAddButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), i))
    }

    private fun fridgeActivity() {
        val intent = Intent (activity, MainActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun dietActivity() {
        val intent = Intent (activity, MainActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun recipesActivity() {
        val intent = Intent (activity, RecipesActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun favActivity() {
        val intent = Intent (activity, MainActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun calculateKcal(weight : Int, sex : Int): Float {
        if(sex == 0){
            return weight * 24f
        } else {
            return weight * 0.9f * 24
        }
    }


}