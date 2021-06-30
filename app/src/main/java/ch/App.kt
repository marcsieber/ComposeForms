package ch.fhnw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import ch.model.Model
import ch.ui.UI

object App : IApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        Model.connectAndSubscribe()
    }

    @ExperimentalFoundationApi
    @Composable
    override fun createAppUI() {
        UI(Model)
    }
}