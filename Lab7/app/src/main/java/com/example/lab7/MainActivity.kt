package com.example.hw7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Use val for immutable references and type inference
        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        // Use more idiomatic Kotlin collections and functions
        val items = resources.obtainTypedArray(R.array.image_list).use { array ->
            (0 until array.length()).map { index ->
                Item(
                    photo = array.getResourceId(index, 0),
                    name = "水果${index + 1}",
                    price = (10..100).random()
                )
            }
        }

        // Use toList() for count to ensure immutability
        val count = (1..items.size).map { "$it個" }

        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            count
        )

        gridView.apply {
            numColumns = 3
            adapter = MyAdapter(this@MainActivity, items, R.layout.adapter_vertical)
        }

        listView.adapter = MyAdapter(
            this,
            items,
            R.layout.adapter_horizontal
        )
    }
}