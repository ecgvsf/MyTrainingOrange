package com.example.mytrainingorange.WeightPicker

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import com.example.mytrainingorange.UserSettingsActivity
import com.example.mytrainingorange.WeightPicker.ui.theme.CanvasWeightPickerTheme
import com.example.mytrainingorange.databinding.ActivityWeightBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.properties.Delegates

class WeightActivity : ComponentActivity() {

    private lateinit var binding: ActivityWeightBinding
    val databese: DatabaseReference = Firebase.database.reference
    val auth: FirebaseAuth = Firebase.auth

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var flag: Activity
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flag = this
        binding = ActivityWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val w = intent.extras?.get("weight") as Int

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                //Button(onClick = { exit() }) {

                //}
                CanvasWeightPickerTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        WeightPickerScreen(w)
                    }
                }
            }
        }

        binding.backButton.setOnClickListener{
            ActivityCompat.finishAfterTransition(this);
            finish()
        }
        binding.homeButton.setOnClickListener{
            UserSettingsActivity.flag.finish()
            ActivityCompat.finishAfterTransition(this);
            finish()
        }
    }

}
