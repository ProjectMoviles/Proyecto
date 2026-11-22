package com.example.appmedianoche

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNavList = findViewById<Button>(R.id.btnNavList)

        // Back to the list screen
        btnNavList.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        // TODO-> real map with the dishes
        // TODO-> filters (rarity, price, nearby)
        // TODO-> fill the selected dish card and "View details" button
    }
}