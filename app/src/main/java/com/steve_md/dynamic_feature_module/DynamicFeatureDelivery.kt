package com.steve_md.dynamic_feature_module

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class DynamicFeatureDelivery : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        SplitCompat.install(this)
    }
}