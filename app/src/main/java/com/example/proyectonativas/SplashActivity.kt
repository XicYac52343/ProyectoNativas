package com.example.proyectonativas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginregistroActivity::class.java)
            startActivity(intent)
            finish()
        },1500)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Activity splash", "esta en estado on start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity splash", "esta en estado onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity splash", "esta en estado onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity splash", "esta en estado onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity splash", "esta en estado onDestroy")

    }
}