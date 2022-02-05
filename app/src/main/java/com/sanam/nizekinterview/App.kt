package com.sanam.nizekinterview

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.*
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.firebase.inject.Provider
import com.sanam.nizekinterview.common.sessionManager.SessionManagerService
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), LifecycleObserver, Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}



