package com.example.mytrainingorange.model

class Diet(val title : String? = null, val desc : String? = null, val calMultiplier : Float? = null, val maxProteins : Float? = null, val maxCarbo : Float? = null,
           val maxFats : Float? = null, val minProteins : Float? = null, val minCarbo : Float? = null, val minFats : Float? = null,
           val vitaminsAndMinerals : Int? = null, val cholesterol : Float? = null, val suggestedFood: List<Food>? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}