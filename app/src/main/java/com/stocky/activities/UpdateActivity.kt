package com.stocky.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.StorageReference
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.*
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import androidx.core.content.FileProvider
import com.stocky.BuildConfig
import com.stocky.R
import com.stocky.databinding.ActivityUpdateBinding
import java.io.File

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding

    private lateinit var mStorageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mStorageRef = FirebaseStorage.getInstance().reference
        doUpdate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (packageManager.canRequestPackageInstalls()) {
                    downloadLink
                }
            } else {
                Toast.makeText(this, "Algum erro ocorreu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doUpdate() {
        val btnUpdate = binding.btnUpdate
        btnUpdate.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@UpdateActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!packageManager.canRequestPackageInstalls()) {
                        startActivityForResult(
                            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(
                                Uri.parse(String.format("package:%s", packageName))
                            ), 1234
                        )
                    } else {
                        downloadLink
                    }
                } else {
                    downloadLink
                }
            }
        }
    }

    private val downloadLink: Unit
        get() {
            mStorageRef.child("stocky.apk").downloadUrl
                .addOnSuccessListener { uri -> downloadFile(uri) }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    Toast.makeText(this@UpdateActivity, "Ocorreu algum erro", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    private fun downloadFile(uri: Uri) {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "stocky.apk")
        if (file.exists()) {
            file.delete()
            Toast.makeText(
                this@UpdateActivity,
                getString(R.string.old_APK_deleted),
                Toast.LENGTH_LONG
            ).show()
        }
        val downloadDialog = ProgressDialog(this)
        downloadDialog.setTitle(getString(R.string.updating))
        downloadDialog.setMessage(getString(R.string.updating_text))
        downloadDialog.setCancelable(false)
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
            .setTitle(getString(R.string.update))
            .setDescription(getString(R.string.update_desc))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setMimeType("application/vnd.android.package-archive")
            .setDestinationUri(Uri.fromFile(file))
        val id = downloadManager.enqueue(request)
        downloadDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            getString(R.string.cancel)
        ) { _, _ -> downloadManager.remove(id) }
        downloadDialog.show()
        val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                    downloadDialog.dismiss()
                    installApk(file)
                    unregisterReceiver(this)
                    finish()
                }
            }
        }
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun installApk(file: File) {
        val intent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.data = FileProvider.getUriForFile(
                this@UpdateActivity,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
        } else {
            intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        startActivity(intent)
    }
}