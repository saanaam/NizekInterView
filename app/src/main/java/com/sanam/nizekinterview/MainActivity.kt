package com.sanam.nizekinterview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.sanam.nizekinterview.common.sessionManager.SessionManagerService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun configureNavController() {
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onPause() {
        super.onPause()
        Log.e("service", "app moved to background")
        // Created a Work Request
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SessionManagerService>().build()
        // Submit the WorkRequest to the system
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }

    override fun onStart() {
        super.onStart()
        WorkManager.getInstance(this).cancelAllWork()
    }

}