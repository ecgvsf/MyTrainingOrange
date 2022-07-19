package com.example.mytrainingorange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mytrainingorange.databinding.ActivityLogInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra("User", currentUser)
            startActivity(intent)
            finish()
        }

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            logIn()
        }

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.recuperoPw.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content), "Cazzi tua", Snackbar.LENGTH_LONG).show()
        }
    }

    fun logIn(){
        auth.signInWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString())
            .addOnCompleteListener(this) { task ->
                Log.w("Qua", "per caso")
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent (this, MainActivity::class.java)
                    intent.putExtra("User", user)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    message("Accesso non riuscito. Email o Password errati")
                }
            }
    }

    private fun message(s: String) {
        Snackbar.make(findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG).show()
    }
}