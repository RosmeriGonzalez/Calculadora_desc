package com.example.conejossaltarines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Main entry point of the application. It simply inflates the layout that
 * represents the puzzle board of the rabbits.
 *
 * The activity is declared in the manifest with a fixed landscape orientation
 * so that the board is always shown horizontally as required by the app's
 * design.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
