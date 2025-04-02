package com.rijks.museum

import android.app.Application
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AlberHeijnAppApplication : Application(), SingletonImageLoader.Factory by ImageLoaderBuilder() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
