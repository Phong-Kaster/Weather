package com.example.weather.data.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * # [Getting started with WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)
 */
@HiltWorker
class ExpeditedWorker
@AssistedInject
constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    private val TAG = "WorkManager"

    override fun doWork(): Result {

        Log.d(TAG, "doWork - this is ${this.javaClass.simpleName}")

        return Result.success()
    }
}