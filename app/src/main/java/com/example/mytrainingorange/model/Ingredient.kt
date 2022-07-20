package com.example.mytrainingorange.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ingredient(var qnt : String? = null, var food : Food? = null)