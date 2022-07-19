package com.example.mytrainingorange.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*


@IgnoreExtraProperties
data class User(val userName : String? = null, val name: String? = null, val surname: String? = null,
                val bDay : Date? = null, val weight : Int? = null, val height : Int? = null,
                val sex : Int? = null, val cal : Float? = null, val water: Float? = null,
                val proteins : Float? = null, val lipids : Float? = null, val carbo : Float? = null,
                val fibres : Float? = null, val vitaminsMinerals : Int? = null, val diet: Diet? = null) {

    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

