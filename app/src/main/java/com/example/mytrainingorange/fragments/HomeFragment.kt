package com.example.mytrainingorange.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.mytrainingorange.MainActivity
import com.example.mytrainingorange.R
import com.example.mytrainingorange.listener.OnSwipeTouchListener
import com.example.mytrainingorange.model.User
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
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

class HomeFragment : Fragment() {

    private var userId: String? = "00000000"
    private lateinit var database: DatabaseReference

    private lateinit var pieChart: PieChart

    // Image
    private lateinit var profImage: CircleImageView
    private lateinit var dialog: Dialog


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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(
            R.layout.fragment_home,
            container, false
        )


        database = Firebase.database.reference

        //Pie Chart
        pieChart = view.findViewById<View>(R.id.chart) as PieChart
        database.child("users").child(userId.toString()).get().addOnSuccessListener {
            val userData = it.getValue<User>()
            if (userData != null) {
                createPieChart(userData.proteins as Float, userData.carbo as Float, userData.lipids as Float)
            }
            //dialog.dismiss()
        }.addOnFailureListener{
            createPieChart(1f, 1f, 1f)
        }

        //Profile Image
        profImage = view.findViewById<View>(R.id.set_profile_image) as CircleImageView

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

        return view
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

        pieChart.setTransparentCircleColor(Color.BLACK)
        pieChart.setTransparentCircleAlpha(110)
        pieChart.setDrawEntryLabels(false)
        // enable rotation of the chart by touch
        pieChart.isRotationEnabled = false
        pieChart.isHighlightPerTapEnabled = false
        pieChart.holeRadius = 70F
        pieChart.transparentCircleRadius = 74f
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        // entry label styling
        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.data = pieData
        pieChart.setHoleColor(1)

        pieChart.description

        //legend
        val l = pieChart.legend
        l.formSize = 10f // set the size of the legend forms/shapes

        l.form = Legend.LegendForm.CIRCLE // set what type of form/shape should be used

        l.yOffset = 0f
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //l.verticalAlignment =
            //Legend.LegendVerticalAlignment.BOTTOM // set vertical alignment for legend

        l.horizontalAlignment =
            Legend.LegendHorizontalAlignment.CENTER // set horizontal alignment for legend

        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.textSize = 12f
        l.textColor = Color.WHITE
        //l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        //l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.xEntrySpace = 20f // set the space between the legend entries on the x-axis

        // set custom labels and colors
        // set custom labels and colors
        val entries: MutableList<LegendEntry> = ArrayList()
        val yValues: ArrayList<PieEntry> = ArrayList()
        yValues.add(PieEntry(carbo, ""))
        yValues.add(PieEntry(lipids, ""))
        yValues.add(PieEntry(proteins, ""))
        yValues.add(PieEntry(0f, ""))
        for (i in 0..2) {
            val entry = LegendEntry()
            entry.formColor = ColorTemplate.MATERIAL_COLORS [i]
            entry.label = yValues[i].label
            entries.add(entry)

        }
        l.setCustom(entries)

        val d = Description()
        d.text = " "
        pieChart.description = d
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