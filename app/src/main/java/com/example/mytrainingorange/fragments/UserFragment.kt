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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val GalleryPick = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentUserBinding.inflate(inflater, container, false)

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