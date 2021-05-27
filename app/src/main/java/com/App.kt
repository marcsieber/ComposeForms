package com.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import com.IApp
import com.model.Model
import com.ui.UI

object App : IApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        Model.connectAndSubscribe()
    }

    @Composable
    override fun createAppUI() {
        UI(Model)
    }
}