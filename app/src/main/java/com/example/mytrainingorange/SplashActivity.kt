package com.example.mytrainingorange

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView

class SplashActivity : AppCompatActivity() {

    private lateinit var video: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //video = findViewById(R.id.video)
        //val videoPathL = "android.resource//com.example.mytrainingorange/raw/" + R.raw.splash_screen_light
        //val videoPathD = "android.resource//" + packageName + "/" + R.raw.splash_screen_night
        //val uriL = Uri.parse(videoPathL)
        //val uriD = Uri.parse(videoPathD)
        //video.setVideoPath("android.resource//" + packageName + "/" + R.raw.splash_screen_night)
        //video.start()

        /*when (resources.configuration.uiMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                video.setVideoURI(uriL)
                video.start()
            }
            // Night mode is not active, we're in day time
            Configuration.UI_MODE_NIGHT_YES -> {
                video.setVideoURI(uriD)
                video.start()
            }
            // Night mode is active, we're at night!
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                video.setVideoURI(uriL)
                video.start()
            }
            // We don't know what mode we're in, assume notnight
        }

         */

        Handler().postDelayed({
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        },1000)

    }
}