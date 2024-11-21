package com.example.lab9_1

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var progressRabbit = 0
    private var progressTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        btnStart.setOnClickListener {
            startRace()
        }
    }

    private fun startRace() {
        btnStart.isEnabled = false
        progressRabbit = 0
        progressTurtle = 0
        sbRabbit.progress = 0
        sbTurtle.progress = 0

        coroutineScope.launch {
            val rabbitJob = launch { runRabbit() }
            val turtleJob = launch { runTurtle() }
            rabbitJob.join()
            turtleJob.join()
        }
    }

    private suspend fun runRabbit() {
        val sleepProbability = listOf(true, true, false)
        while (progressRabbit < 100 && progressTurtle < 100) {
            delay(100)
            if (sleepProbability.random()) delay(300)
            progressRabbit += 3
            sbRabbit.progress = progressRabbit

            if (progressRabbit >= 100 && progressTurtle < 100) {
                showToast("兔子勝利")
                btnStart.isEnabled = true
                return
            }
        }
    }

    private suspend fun runTurtle() {
        while (progressTurtle < 100 && progressRabbit < 100) {
            delay(100)
            progressTurtle += 1
            sbTurtle.progress = progressTurtle

            if (progressTurtle >= 100 && progressRabbit < 100) {
                showToast("烏龜勝利")
                btnStart.isEnabled = true
                return
            }
        }
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}