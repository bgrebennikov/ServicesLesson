package com.bgrebennikov.github.serviceslesson

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.*

class JobServiceMain : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onStartJob(p0: JobParameters?): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutineScope.launch {
                var workItem = p0?.dequeueWork()
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)

                    for (i in 0 until 15) {
                        delay(1000L)
                        Log.i(TAG, "page: $page :$i")
                    }
                    p0?.completeWork(workItem)
                    workItem = p0?.dequeueWork()
                }
                jobFinished(p0, false)
            }
        }

        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.i(TAG, "onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        Log.i(TAG, "onDestroy")
    }

    companion object {
        const val TAG = "MESSAGE"
        const val JOB_ID = 1

        private const val PAGE = "page"

        fun newBundle(page: Int): PersistableBundle {
            return PersistableBundle().apply {
                putInt(PAGE, page)
            }
        }

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }

    }

}