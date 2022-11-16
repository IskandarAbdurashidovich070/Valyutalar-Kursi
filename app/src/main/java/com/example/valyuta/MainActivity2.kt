package com.example.valyuta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        Handler(Looper.myLooper()!!).postDelayed({
            finish()
            startActivity(Intent(this@MainActivity2, MainActivity::class.java))
        }, 3000)
    }
}