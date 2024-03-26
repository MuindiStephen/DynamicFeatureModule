package com.steve_md.dynamic_feature_module

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.BuildConfig
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.steve_md.dynamic_feature_module.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var mySessionId: Int? = null
    private val progressDialog = ProgressDialog(this)
    private val callback: DynamicDeliveryCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDownloadOnDemandModule.setOnClickListener {
            downloadDynamicModule()
        }
        binding.buttonOpenOnInstallDynamicFeature.setOnClickListener {
            val intent = Intent()
            intent.setClassName("com.steve_md.dynamic_feature_module","com.steve_md.oninstall.MainActivity")
            startActivity(intent)
        }

    }

    private fun downloadDynamicModule() {
        val splitInstallManager: SplitInstallManager = SplitInstallManagerFactory.create(this)
        val splitInstallRequest: SplitInstallRequest = SplitInstallRequest
            .newBuilder()
            .addModule("dynamic-feature-test")
            .build()

        val listener =
            SplitInstallStateUpdatedListener { splitInstallSessionState ->
                if (splitInstallSessionState.sessionId() == mySessionId) {
                    when (splitInstallSessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> run {
                            Log.d(TAG, "Dynamic Module installed")
                            Toast.makeText(
                                this@MainActivity,
                                "Dynamic Module downloaded",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.i(this@MainActivity.toString(), "===Installed===>{}")

                            val intent = Intent()
                            intent.setClassName("com.steve_md.dynamic_feature_module","com.steve_md.dynamic_feature_test.DynamicTestMainActivity")
                            startActivity(intent)
                        }

                        SplitInstallSessionStatus.CANCELED -> run {
                            Log.e(this@MainActivity.toString(), "===Cancelled===>")
                        }

                        SplitInstallSessionStatus.CANCELING -> run {
                            progressDialog.setMessage("Cancelling")
                            progressDialog.show()
                        }

                        SplitInstallSessionStatus.DOWNLOADED -> run {
                            progressDialog.setMessage("Downloaded")
                            progressDialog.show()
                        }

                        SplitInstallSessionStatus.DOWNLOADING -> run {
                            progressDialog.setMessage("Downloading...")
                            progressDialog.show()
                        }

                        SplitInstallSessionStatus.FAILED -> run {
                            Log.e(this@MainActivity.toString(), "===Failed===>")
                        }

                        SplitInstallSessionStatus.INSTALLING -> run {
                            progressDialog.setMessage("Installing")
                            progressDialog.show()
                        }

                        SplitInstallSessionStatus.PENDING -> run {
                            Log.e(this@MainActivity.toString(), "===Pending===>")
                        }

                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> run {
                            Log.e(this@MainActivity.toString(), "===>Requires user information")
                        }

                        SplitInstallSessionStatus.UNKNOWN -> run {
                            Log.e(this@MainActivity.toString(), "===Unknown===>")
                        }
                    }
                }


            }
        /**
        @splitInstallManager
        @splitInstallRequest
        @listener
         */
        splitInstallManager.registerListener(listener)
        splitInstallManager.startInstall(splitInstallRequest)
            .addOnSuccessListener { sessionId ->
                mySessionId = sessionId
            }
            .addOnFailureListener { exception ->
                Log.d(this@MainActivity.toString(), exception.message.toString())
                handleInstallFailure((exception as SplitInstallException).errorCode)
            }

    }

    private fun handleInstallFailure(errorCode: Int) {

        when (errorCode) {
            SplitInstallErrorCode.NETWORK_ERROR -> {
                callback?.onFailed("No internet found")
            }

            SplitInstallErrorCode.MODULE_UNAVAILABLE -> {
                callback?.onFailed("Module unavailable")
            }

            SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> {
                callback?.onFailed("Active session limit exceeded")
            }

            SplitInstallErrorCode.INSUFFICIENT_STORAGE -> {
                callback?.onFailed("Insufficient storage")
            }

            SplitInstallErrorCode.PLAY_STORE_NOT_FOUND -> {
                callback?.onFailed("Google Play Store Not Found!")
            }

            else -> {
                callback?.onFailed("Something went wrong! Try again later")
            }
        }
    }


}
interface DynamicDeliveryCallback {
    fun onFailed(errorMessage: String)
}