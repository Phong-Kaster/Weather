package com.example.weather.core

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class CoreViewModel
@Inject
constructor() : ViewModel() {
    val TAG = this::class.java.simpleName
}