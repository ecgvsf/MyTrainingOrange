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
import com.example.mytrainingorange.R
import com.example.mytrainingorange.buttonactivity.RecipesActivity
import com.example.mytrainingorange.databinding.FragmentDietBinding
import com.example.mytrainingorange.listener.CustomAnimationDrawableNew
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DietFragment : Fragment() {
    private var _binding: FragmentDietBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var database : DatabaseReference

    private lateinit var glasses : ArrayList<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietBinding.inflate(inflater, container, false)

        database = Firebase.database.reference
        /*
        val bars = arrayOf(binding.kcalProgressBar,
            binding.waterProgressBar,
            binding.carboProgressBar,
            binding.proteinsProgressBar,
            binding.lipidsProgressBar)

        database.child("users").child(userId.toString()).get().addOnSuccessListener {
            val userData = it.getValue<User>()
            if (userData != null) {

            }
        }*/

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
        val intent = Intent (activity, MainActivity::class.java)
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



}