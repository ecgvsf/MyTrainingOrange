package com.example.mytrainingorange.model

class Food(val id : Int? = null, val name : String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}