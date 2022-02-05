package com.sanam.nizekinterview.common.sessionManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.sanam.nizekinterview.common.Const.BACKGROUNDTIMEAUTOLOGOUT
import com.sanam.nizekinterview.common.utils.launchPeriodicAsync
import com.sanam.nizekinterview.data.cache.sharepreference.token.TokenDataSource
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltWorker
class SessionManagerService @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(appContext, params) {
    @Inject
    lateinit var tokenDataSource: TokenDataSource
    lateinit var job: Job

    override suspend fun doWork(): Result = coroutineScope {
        try {
            job = this.launchPeriodicAsync(BACKGROUNDTIMEAUTOLOGOUT) {
                tokenDataSource.clearToken()
                job.cancel()
            }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }

    }


}