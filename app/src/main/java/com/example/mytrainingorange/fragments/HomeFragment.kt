package com.example.mytrainingorange.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrainingorange.R
import com.example.mytrainingorange.adapter.RecycleAdapter
import com.example.mytrainingorange.databinding.FragmentHomeBinding
import com.example.mytrainingorange.model.Diet
import com.example.mytrainingorange.model.Recipe
import com.example.mytrainingorange.model.User
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.roundToInt

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var userId: String? = "00000000"
    private lateinit var database: DatabaseReference

    private lateinit var dialog: Dialog

    // Image
    private lateinit var profImage: CircleImageView
    private lateinit var recipes: ArrayList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("UserId").toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        userId = arguments?.getString("UserId").toString()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        database = Firebase.database.reference

        //Recipes
        createRecipesView()

        //Pie Chart
        database.child("users").child(userId.toString()).get().addOnSuccessListener {
            val userData = it.getValue<User>()
            if (userData != null) {
                createPieChart(userData.proteins as Float, userData.carbo as Float, userData.fats as Float)

                var calToHit = calculateKcal(userData.weight!!, userData.sex!!)

                var currentData = userData.carbo!!
                var dataToHit = (((userData.diet as Diet).maxCarbo!! + (userData.diet).minCarbo!!) / 2) / 100 * calToHit / 4
                binding.carboTxt.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"

                currentData = userData.proteins!!
                dataToHit = (((userData.diet).maxProteins!! + (userData.diet).minProteins!!) / 2) / 100 * calToHit / 4
                binding.proteinsTxt.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"

                currentData = userData.fats!!
                dataToHit = (((userData.diet).maxFats!! + (userData.diet).minFats!!) / 2) / 100 * calToHit / 9
                binding.fatsTxt.text = "${currentData.roundToInt()}/${dataToHit.roundToInt()} g"
            }
        }.addOnFailureListener{
            createPieChart(1f, 1f, 1f)
        }

        //Profile Image
        binding.setProfileImage

        // handle the Choose Image button to trigger
        // the image chooser function
        // handle the Choose Image button to trigger
        // the image chooser function

        /*
        val layout = view.findViewById<FrameLayout>(R.id.layout)
        layout.setOnTouchListener(object : OnSwipeTouchListener(this@HomeFragment.context) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                (activity as MainActivity?)!!.openMenu()
            }
        })
         */

        return binding.root
    }

    private fun createRecipesView(){
        recipes = ArrayList()
        recipes.add(Recipe(R.drawable.risotto_ripieno, "risotto_ripieno", "Description"))
        recipes.add(Recipe(R.drawable.friselle, "friselle", "Description"))
        recipes.add(Recipe(R.drawable.avocado_toast, "avocado_toast", "Description"))
        recipes.add(Recipe(R.drawable.coccoretti, "coccoretti", "Description"))
        recipes.add(Recipe(R.drawable.gamberi_lime, "gamberi_lime", "Description"))
        recipes.add(Recipe(R.drawable.ricetta_uova, "ricetta_uova", "Description"))


        binding.recycler.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = RecycleAdapter(requireContext(), recipes)
        binding.recycler.scrollToPosition((Int.MAX_VALUE/2)-(Int.MAX_VALUE/2)%recipes.size)

        binding.recycler.addOnScrollListener(
            CircularScrollListener()
        )
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

    private fun createPieChart(proteins: Float, carbo: Float, lipids: Float) {
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(carbo, "CARBO"))
        pieEntries.add(PieEntry(lipids, "LIPIDS"))
        pieEntries.add(PieEntry(proteins, "PROTEINS"))

        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS , 255)
        pieDataSet.setDrawValues(false)

        val pieData = PieData(pieDataSet)

        binding.chart.setTransparentCircleColor(Color.BLACK)
        binding.chart.setTransparentCircleAlpha(110)
        binding.chart.setDrawEntryLabels(false)
        // enable rotation of the chart by touch
        binding.chart.isRotationEnabled = false
        binding.chart.isHighlightPerTapEnabled = false
        binding.chart.holeRadius = 70F
        binding.chart.transparentCircleRadius = 74f
        binding.chart.animateY(1400, Easing.EaseInOutQuad)
        // entry label styling
        // entry label styling
        binding.chart.setEntryLabelColor(Color.WHITE)
        binding.chart.setEntryLabelTextSize(12f)
        binding.chart.data = pieData
        binding.chart.setHoleColor(1)

        binding.chart.description

        //legend
        val l = binding.chart.legend
        l.formSize = 15f // set the size of the legend forms/shapes

        l.form = Legend.LegendForm.CIRCLE // set what type of form/shape should be used

        l.yOffset = 0f
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.verticalAlignment =
        //Legend.LegendVerticalAlignment.BOTTOM // set vertical alignment for legend

        l.horizontalAlignment =
            Legend.LegendHorizontalAlignment.CENTER // set horizontal alignment for legend

        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.textSize = 0f
        l.textColor = Color.WHITE
        //l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        //l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.xEntrySpace = 30f // set the space between the legend entries on the x-axis
        l.xOffset = -10f

        // set custom labels and colors
        // set custom labels and colors
        val entries: MutableList<LegendEntry> = ArrayList()
        val yValues: ArrayList<PieEntry> = ArrayList()
        yValues.add(PieEntry(carbo, ""))
        yValues.add(PieEntry(lipids, ""))
        yValues.add(PieEntry(proteins, ""))
        for (i in 0..2) {
            val entry = LegendEntry()
            entry.formColor = ColorTemplate.MATERIAL_COLORS [i]
            entry.label = yValues[i].label
            entries.add(entry)

        }
        l.setCustom(entries)

        val d = Description()
        d.text = " "
        binding.chart.description = d
    }

    private fun calculateKcal(weight : Int, sex : Int): Float {
        if(sex == 0){
            return weight * 24f
        } else {
            return weight * 0.9f * 24
        }
    }
    private fun loadingDialog() {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_loading)

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT

        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(R.color.primary))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}