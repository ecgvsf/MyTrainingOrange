package com.example.mytrainingorange.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingorange.*
import com.example.mytrainingorange.buttonactivity.DietActivity
import com.example.mytrainingorange.databinding.FragmentUserBinding
import com.example.mytrainingorange.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var databese : DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User

    private val GalleryPick = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentUserBinding.inflate(inflater, container, false)

        databese = Firebase.database.reference
        auth = Firebase.auth

        refreshData()

        binding.refreshLayout.setOnRefreshListener{
            refreshData()
            binding.refreshLayout.isRefreshing = false
        }

        binding.photoButton.setOnClickListener{
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GalleryPick)
        }
        binding.setProfileImage.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GalleryPick)
        }
        binding.dettagli.setOnClickListener {
            val intent = Intent (context, UserSettingsActivity::class.java)
            //intent.putExtra("h", user.height)
            //intent.putExtra("w", user.weight)
            //intent.putExtra("s", user.sex)
            //intent.putExtra("y", user.bDay?.year)
            //intent.putExtra("m", user.bDay?.month)
            //intent.putExtra("d", user.bDay?.date)
            //intent.putExtra("n", user.name)
            //intent.putExtra("s", user.surname)
            //intent.putExtra("u", user.userName)
            startActivityForResult(intent, 0)
        }
        binding.preferenza.setOnClickListener {
            val intent = Intent (context, DietActivity::class.java)
            startActivityForResult(intent, 1)
        }
        binding.abitudini.setOnClickListener {
            val intent = Intent (context, MainActivity::class.java)
            startActivityForResult(intent, 2)
        }
        binding.logOut.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LogInActivity::class.java))
            MainActivity.flag.finish()
        }
        return binding.root
    }

    private fun refreshData() {
        databese.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            user = it.getValue<User>()!!
            binding.name.text = user.name
            binding.dieta.text = "Diet " + user.diet?.title
            binding.valPeso.text = user.weight.toString() + " kg"
            binding.valDieta.text = user.diet?.desc
        }
    }

    private fun bitmapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GalleryPick && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            try {
                val selectedImage = data.data
                val imageStream: InputStream? = context?.contentResolver?.openInputStream(selectedImage!!)
                binding.setProfileImage.setImageBitmap(BitmapFactory.decodeStream(imageStream))
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }
}