package com.steve_md.dynamic_feature_test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitcompat.SplitCompat

class DynamicTestMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_test_main)
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        SplitCompat.install(this)
    }
}