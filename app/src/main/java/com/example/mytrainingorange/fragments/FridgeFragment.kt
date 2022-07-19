package com.example.mytrainingorange.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.bubblepickerlibrary.BubblePickerListener
import com.example.bubblepickerlibrary.adapter.BubblePickerAdapter
import com.example.bubblepickerlibrary.model.BubbleGradient
import com.example.bubblepickerlibrary.model.PickerItem
import com.example.bubblepickerlibrary.rendering.BubblePicker
import com.example.mytrainingorange.R
import com.example.mytrainingorange.databinding.FragmentFridgeBinding

class FridgeFragment : Fragment() {

    private var _binding: FragmentFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var picker: BubblePicker


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(
            R.layout.fragment_fridge,
            container, false
        )
        _binding = FragmentFridgeBinding.inflate(inflater, container, false)


        val ingredients = arrayOf(
            "aduki", "agar", "alfalfa", "almond", "almond",
            "almond milk", "almond paste", "anacardi", "anchovies", "anchovies oil",
            "anchovies salted", "anona", "annurche", "apple dry", "empire"
        )

        val images = arrayOf(
            R.drawable.aduki_beans, R.drawable.agar_agar, R.drawable.alfalfa_sprouts,
            R.drawable.almond_dried, R.drawable.almond_fresh, R.drawable.almond_milk,
            R.drawable.almond_paste, R.drawable.anacardi, R.drawable.anchovies,
            R.drawable.anchovies_oil, R.drawable.anchovies_salted, R.drawable.anona_cherimoya,
            R.drawable.apple_annurche, R.drawable.apple_dry, R.drawable.apple_empire
        )

        val color = Color.parseColor("#FF9800")


        picker = view.findViewById<BubblePicker>(R.id.picker)
        binding.picker.adapter = object : BubblePickerAdapter {

            override val totalCount = ingredients.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = ingredients[position]
                    gradient = BubbleGradient(
                        color,
                        color + 100,
                        BubbleGradient.VERTICAL)
                    textColor = ContextCompat.getColor(requireContext(), android.R.color.white)
                    textSize = 25F
                    backgroundImage = ContextCompat.getDrawable(requireContext(), images[position])
                }
            }
        }

        binding.picker.setZOrderOnTop(false)
        binding.picker.bubbleSize = 1
        binding.picker.centerImmediately = true

        binding.picker.listener = object : BubblePickerListener {
            override fun onBubbleDeselected(item: PickerItem) {

            }

            override fun onBubbleSelected(item: PickerItem) {

            }
        }
        return  binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.picker.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.picker
    }

}