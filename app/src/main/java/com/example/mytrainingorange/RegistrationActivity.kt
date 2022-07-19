package com.example.mytrainingorange

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingorange.databinding.ActivityRegistrationBinding
import com.example.mytrainingorange.model.Diet
import com.example.mytrainingorange.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var  database : DatabaseReference
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var flag: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.register.setOnClickListener{
            database = Firebase.database.reference
            register()
        }

        binding.accedi.setOnClickListener{
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }

    }

    public override fun onStart() {
        super.onStart()
        flag = this
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent (this, MainActivity::class.java)
            intent.putExtra("User", currentUser)
            startActivity(intent)
            finish()
        }
    }

    fun register(){
        if(!binding.emailText.text.toString().contains("@") || binding.emailText.text.toString() == ""){
            message("Inserisci una Email valida")
            return
        }
        if(binding.usernameText.text.toString().length <= 6){
            message("Username non valido, prova ad inserirne uno compreso fra 6 e 20 caratteri")
            return
        }
        if(binding.passwordText.text.toString() != (binding.confermaPasswordText.text.toString())){
            message("Le password non corrispondono")
            return
        }
        if(binding.passwordText.text.toString().length <= 8){
            message("Password troppo corta")
            return
        }
        if((!binding.passwordText.text.toString().contains("$") &&
                    !binding.passwordText.text.toString().contains("&") &&
                    !binding.passwordText.text.toString().contains("*") &&
                    !binding.passwordText.text.toString().contains("#") &&
                    !binding.passwordText.text.toString().contains("@") &&
                    !binding.passwordText.text.toString().contains("-") &&
                    !binding.passwordText.text.toString().contains("_") &&
                    !binding.passwordText.text.toString().contains(".") &&
                    !binding.passwordText.text.toString().contains("+")) ||
            (binding.passwordText.text.toString() == (binding.passwordText.text.toString()
                .lowercase(Locale.getDefault())))){
            message("Inserisci una password più forte. Deve contenere almeno un simbolo speciale e una lettera maiuscola")
            return
        }
        if(
            binding.emailText.text.toString() == "" ||
            binding.passwordText.text.toString() == "" ||
            binding.confermaPasswordText.text.toString() == "" ||
            binding.nameText.text.toString() == "" ||
            binding.surnameText.text.toString() == "" ||
            binding.usernameText.text.toString() == ""
        )
        {
            message("Riempi tutti i campi")
            return
        }
        auth.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent (this, MainActivity::class.java)
                    intent.putExtra("User", user)
                    writeNewUser(user!!.uid)
                    startActivity(intent)
                    flag.finish()
                    /*user!!.getIdToken(true).addOnSuccessListener { result ->
                        val idToken = result.token
                        Log.w("ID", idToken.toString())
                        intent.putExtra("UserId", idToken)
                        writeNewUser(idToken)
                        message("Registrazione riuscita")
                        startActivity(intent)
                    } */
                    return@addOnCompleteListener

                } else {
                    // If sign in fails, display a message to the user.
                    message("Qualcosa è andato storto!")
                }
            }
        return
    }

    private fun writeNewUser(uid: String) {
        database.child("users").child(uid).setValue(User(
            binding.usernameText.text.toString(), binding.nameText.text.toString(), binding.surnameText.text.toString(),
            Date(), 0, 0, null, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0, Diet()
        ))

    }

    private fun message(s: String) {
        Snackbar.make(findViewById(com.google.android.material.R.id.content), s, Snackbar.LENGTH_LONG).show()
    }


}