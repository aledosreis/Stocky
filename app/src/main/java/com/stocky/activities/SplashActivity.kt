package com.stocky.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.stocky.service.FirebasePushNotification
import android.content.pm.PackageManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.stocky.BuildConfig
import java.util.HashMap

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var currentVersion: String
    private lateinit var latestVersion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, FirebasePushNotification::class.java))

        currentVersion = BuildConfig.VERSION_NAME

        fetchUpdate()
    }

    private fun fetchUpdate() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(600)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        val defaultValue: MutableMap<String, Any?> = HashMap()
        defaultValue[KEY_UPDATE_VERSION] = currentVersion
        remoteConfig.setDefaultsAsync(defaultValue)
        remoteConfig.fetchAndActivate().addOnCompleteListener(this) { task ->
            latestVersion = if (task.isSuccessful)
                remoteConfig.getString(KEY_UPDATE_VERSION)
            else currentVersion
            goToApp()
        }
    }

    private fun goToApp() {
        val intent = if (latestVersion.toFloat() > currentVersion.toFloat())
            Intent(this, UpdateActivity::class.java)
        else Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val KEY_UPDATE_VERSION = "latestVersion"
    }
}