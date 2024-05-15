package com.victor.alphabank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlphaBankApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}