package com.example.mytrainingorange.WeightPicker.models

sealed class LineType {
    object Normal : LineType()
    object FiveStep : LineType()
    object TenStep : LineType()
}