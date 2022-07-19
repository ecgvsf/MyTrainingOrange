package com.example.mytrainingorange

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingorange.WeightPicker.WeightActivity
import com.example.mytrainingorange.databinding.ActivityUserSettingsBinding
import com.example.mytrainingorange.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.kevalpatel2106.rulerpicker.RulerValuePicker
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import java.util.*


class UserSettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserSettingsBinding

    private lateinit var databese : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var flag: Activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        flag = this

        databese = Firebase.database.reference
        auth = Firebase.auth

        binding = ActivityUserSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databese.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            user = it.getValue<User>()!!
        }

        binding.backButton.setOnClickListener { finish() }
        binding.homeButton.setOnClickListener {
            finish()
        }

        binding.nome.setOnClickListener{
            showNameDialog()
        }

        binding.peso.setOnClickListener{
            intent = Intent(this, WeightActivity::class.java)
            intent.putExtra("weight", user.weight)
            startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        binding.altezza.setOnClickListener{
            showAltezzaDialog()
        }

        binding.data.setOnClickListener{
            showAgeDialog()
        }

        binding.sesso.setOnClickListener{
            showSexDialog()
        }

        binding.term.setOnClickListener{
            startActivity(Intent())
        }

    }

    private fun showNameDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_name)

        val name : TextInputEditText = dialog.findViewById(R.id.name_text)
        val surname : TextInputEditText = dialog.findViewById(R.id.surname_text)

        name.setText(user.name)
        surname.setText(user.surname)

        dialog.findViewById<TextView>(R.id.done).setOnClickListener{
            databese.child("users").child(auth.currentUser!!.uid).child("name")
                .setValue(name.text.toString())
            databese.child("users").child(auth.currentUser!!.uid).child("surname")
                .setValue(surname.text.toString()).addOnCompleteListener{
                    Snackbar.make(findViewById(android.R.id.content), "Operazione completata con successo", Snackbar.LENGTH_LONG).show()
                    dialog.dismiss()
                }

        }

        dialog.findViewById<TextView>(R.id.exit).setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }


    private fun showAltezzaDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_height)

        val h = 175//user.height
        Log.w("altezza", "$h")

        val ruler = dialog.findViewById<RulerValuePicker>(R.id.ruler_picker)
        var height = dialog.findViewById<TextView>(R.id.height)
        ruler.setIndicatorHeight(0.35f,0.15f)
        ruler.selectValue(h)
        ruler.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                //Value changed and the user stopped scrolling the ruler.
                //You can consider this value as final selected value.
                height.text = ruler.currentValue.toString() + " cm"
            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
                height.text = ruler.currentValue.toString() + " cm"
            }
        })

        dialog.findViewById<Button>(R.id.height_button).setOnClickListener{
            databese.child("users").child(auth.currentUser!!.uid).child("height")
                .setValue(ruler.currentValue)
            dialog.dismiss()
        }



        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }


    private fun showSexDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_sex)

        dialog.findViewById<RadioGroup>(R.id.buttons).check(0)//user.sex as Int)

        dialog.findViewById<TextView>(R.id.done).setOnClickListener {
            if (dialog.findViewById<RadioButton>(R.id.male_button).isChecked) {
                databese.child("users").child(auth.currentUser!!.uid).child("sex")
                    .setValue(0)
            } else {
                databese.child("users").child(auth.currentUser!!.uid).child("sex")
                    .setValue(1)
            }
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.exit).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun showAgeDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_age)

        val year: Int = 8//user.bDay?.year as Int
        val month: Int = 8//user.bDay?.month as Int
        val date: Int = 7//user.bDay?.date as Int
        val picker = dialog.findViewById<DatePicker>(R.id.date_picker)

        picker.updateDate(year, month, date)

        dialog.findViewById<TextView>(R.id.done).setOnClickListener {
            databese.child("users").child(auth.currentUser!!.uid).child("bDay")
                .setValue(Date(picker.year, picker.month + 1, picker.dayOfMonth))
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.exit).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}