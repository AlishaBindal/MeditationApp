package com.example.alishameditationapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.alishameditationapp.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<LinearLayout>(R.id.layoutBottom).visibility = View.VISIBLE
        }, 3000)

        findViewById<Button>(R.id.btnGetStarted).setOnClickListener {
            initializeLoginFlow()
        }
    }

    private fun initializeLoginFlow() {
        findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navigateToMain()
        } else {
            showFirebaseUI()
        }
    }

    private fun showFirebaseUI() {
        val providers: List<AuthUI.IdpConfig> = Arrays.asList(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_AlishaMeditationApp)
                .build(), RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                navigateToMain()
            } else {
                finish()
            }
        }
    }

    private fun navigateToMain() {
        findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }
}