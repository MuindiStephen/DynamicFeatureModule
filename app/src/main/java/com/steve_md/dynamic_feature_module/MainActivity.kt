package com.steve_md.dynamic_feature_module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : AppCompatActivity() {
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

    }


}