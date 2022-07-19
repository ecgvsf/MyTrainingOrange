package com.example.mytrainingorange.WeightPicker

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.mytrainingorange.WeightPicker.components.WeightScale
import com.example.mytrainingorange.WeightPicker.models.ScaleStyle
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.sync.Semaphore


@ExperimentalAnimationApi
@Composable
fun WeightPickerScreen(userw: Int) {
    mostra(w = userw)
}

@Composable
fun mostra(w: Int){
    val databese: DatabaseReference = Firebase.database.reference
    val auth: FirebaseAuth = Firebase.auth

    Log.w("Peso", "$w")

    var weight by remember {
        mutableStateOf(w)
    }
    val haptic = LocalHapticFeedback.current
    Box(modifier = Modifier.fillMaxSize()) {
        WeightScale(
            modifier = Modifier
                .fillMaxWidth()
                .height(650.dp)
                .align(Alignment.BottomCenter),
            style = ScaleStyle(
                scaleWidth = 240.dp

            ),
            initialWeight = w,
            onWeightChange = {
                weight = it
                when {
                    it % 10 == 0 -> {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                }
            }
        )
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
                .height(270.dp)
                .align(Alignment.BottomCenter),
            text = "$weight Kg",
            fontSize = 52.sp
        )
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    databese.child("users").child(auth.currentUser!!.uid).child("weight")
                        .setValue(weight)
                    ActivityCompat.finishAfterTransition(WeightActivity.flag);
                    WeightActivity.flag.finish()

                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .offset(75.dp, 620.dp)
            ) {
                Text(
                    text = "Select weight",
                    color = Color.White,
                    fontFamily = FontFamily.Default,
                )
            }
        }
    }
}

