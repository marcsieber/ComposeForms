package ch.fhnw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable

interface IApp {

    /**
     * Initialer Aufbau des App-Status.
     *
     * Bereitstellen aller initial notwendigen Daten (Daten werden normalerweise erst
     * geladen, wenn sie fuer die Anzeige benötigt werden).
     */
    fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?)


    /**
     * Das gesamte UI der App.
     */
    @Composable
    fun createAppUI()


    /**
     * Wird aufgerufen sobald die App nicht mehr im Vordergrund sichtbar ist.
     */
    fun onStop(activity: AppCompatActivity) {
        // Default: Nichts zu tun
    }
}