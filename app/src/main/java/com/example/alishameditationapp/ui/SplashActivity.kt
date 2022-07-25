package com.example.alishameditationapp.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.alishameditationapp.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class SplashActivity : AppCompatActivity() {

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
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
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
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.Theme_AlishaMeditationApp)
            .build()

        startActivityResult.launch(intent)
    }


    private val startActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    navigateToMain()
                }
                RESULT_CANCELED -> {
                    showAlertDialog()
                }
                else -> {
                    showAlertDialog()
                }
            }
        }

    private fun navigateToMain() {
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Login failed, do you want to retry?")
        builder.setCancelable(false)

        builder.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            run {
                initializeLoginFlow()
                dialog.dismiss()
            }
        }

        builder.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.dismiss() }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}