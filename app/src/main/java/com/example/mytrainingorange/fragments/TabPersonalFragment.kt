package com.example.mytrainingorange.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrainingorange.R
import com.example.mytrainingorange.adapter.RecycleAdapter
import com.example.mytrainingorange.adapter.RecycleAdapterMeal
import com.example.mytrainingorange.databinding.FragmentTabPersonalBinding
import com.example.mytrainingorange.model.Recipe

class TabPersonalFragment : Fragment() {

    private var _binding: FragmentTabPersonalBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recipes: ArrayList<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTabPersonalBinding.inflate(inflater, container, false)

        recipes = ArrayList()
        recipes.add(Recipe(R.drawable.risotto_ripieno, "risotto_ripieno", "Description", "234"))
        recipes.add(Recipe(R.drawable.friselle, "friselle", "Description", "33"))
        recipes.add(Recipe(R.drawable.avocado_toast, "avocado_toast", "Description", "238"))
        recipes.add(Recipe(R.drawable.coccoretti, "coccoretti", "Description", "112"))
        recipes.add(Recipe(R.drawable.gamberi_lime, "gamberi_lime", "Description", "85"))
        recipes.add(Recipe(R.drawable.ricetta_uova, "ricetta_uova", "Description", "67"))

        binding.recycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = RecycleAdapterMeal(requireContext(), recipes)

        binding.recycler.addOnScrollListener(
            CircularScrollListener()
        )

        return binding.root
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