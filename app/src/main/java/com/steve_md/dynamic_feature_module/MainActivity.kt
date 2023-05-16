package com.steve_md.dynamic_feature_module

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus


class MainActivity : AppCompatActivity() {

    private val mySessionId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadDynamicModule()
    }

    private fun downloadDynamicModule() {
        val splitInstallManager:SplitInstallManager = SplitInstallManagerFactory.create(this)
        val splitInstallRequest:SplitInstallRequest = SplitInstallRequest
            .newBuilder()
            .addModule("dynamic-feature-test")
            .build()

        val listener =
            SplitInstallStateUpdatedListener { splitInstallSessionState ->
                if (splitInstallSessionState.sessionId() == mySessionId) {
                    when (splitInstallSessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            Log.d(TAG, "Dynamic Module downloaded")
                            Toast.makeText(
                                this@MainActivity,
                                "Dynamic Module downloaded",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        SplitInstallSessionStatus.CANCELED -> {
                            //Log.d("Dynamic module concelled", "")
                        }
                        SplitInstallSessionStatus.CANCELING -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.DOWNLOADED -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.DOWNLOADING -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.FAILED -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.INSTALLING -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.PENDING -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            TODO()
                        }
                        SplitInstallSessionStatus.UNKNOWN -> {
                            TODO()
                        }
                    }
                }
            }

    }


}