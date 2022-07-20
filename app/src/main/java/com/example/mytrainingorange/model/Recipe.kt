package com.example.mytrainingorange.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Recipe(var image: Int? = null, var title: String? = null, var desc: String? = null, var kcal: String? = null, var ingredients : List<Ingredient>?=null)