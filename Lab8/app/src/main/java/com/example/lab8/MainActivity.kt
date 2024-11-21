package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Use mutableListOf for dynamic list management
    private val contacts = mutableListOf<Contact>()
    private lateinit var contactAdapter: MyAdapter

    // Simplified result launcher with more concise handling
    private val addContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val name = intent.getStringExtra("name") ?: return@let
                val phone = intent.getStringExtra("phone") ?: return@let

                contacts.add(Contact(name, phone))
                contactAdapter.notifyItemInserted(contacts.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Use view binding or kotlin synthetics in a real project
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val addButton: Button = findViewById(R.id.btnAdd)

        // Use apply for more concise configuration
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            contactAdapter = MyAdapter(contacts)
            adapter = contactAdapter
        }

        // Use lambda for click listener
        addButton.setOnClickListener {
            val intent = Intent(this, SecActivity::class.java)
            addContactLauncher.launch(intent)
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}