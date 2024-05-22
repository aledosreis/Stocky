package com.stocky.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.stocky.Service.FirebasePushNotification;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private String currentVersion;
    private String latestVersion;
    private static final String KEY_UPDATE_VERSION = "latestVersion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent backgroundService = new Intent(this, FirebasePushNotification.class);
        startService(backgroundService);

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(600)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        Map<String, Object> defaultValue = new HashMap<>();
        defaultValue.put(KEY_UPDATE_VERSION, currentVersion);

        remoteConfig.setDefaultsAsync(defaultValue);

        remoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    latestVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
                    goToApp();
                    Toast.makeText(SplashActivity.this, "Verificação concluida com sucesso.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    latestVersion = currentVersion;
                    goToApp();
                    Toast.makeText(SplashActivity.this, "Verificação falhou.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToApp() {
        if (Float.parseFloat(latestVersion) > Float.parseFloat(currentVersion)) {
            Intent intent = new Intent(this, UpdateActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}