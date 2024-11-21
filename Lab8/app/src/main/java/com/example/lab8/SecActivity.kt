package com.example.lab8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        // Use view binding or kotlin synthetics in a real project
        val nameEditText: EditText = findViewById(R.id.edName)
        val phoneEditText: EditText = findViewById(R.id.edPhone)
        val sendButton: Button = findViewById(R.id.btnSend)

        // Use more concise validation and result handling
        sendButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()

            when {
                name.isBlank() -> showToast("請輸入姓名")
                phone.isBlank() -> showToast("請輸入電話")
                else -> {
                    val resultIntent = Intent().apply {
                        putExtra("name", name)
                        putExtra("phone", phone)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Make showToast an extension function for more idiomatic code
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}