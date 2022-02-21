package com.bgrebennikov.github.serviceslesson

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class JobIntentServiceMain : JobIntentService() {


    override fun onHandleWork(intent: Intent) {
        val page = intent.getIntExtra(PAGE, 0)

        for(i in 0 until 10){
            Thread.sleep(1000L)
            Log.i(TAG, "timer: $i, page:$page")
        }
    }

    companion object{
        const val TAG = "MESSAGE"
        const val PAGE = "page"
        const val JOB_ID = 1

        fun enqueue(context: Context, page: Int){
            enqueueWork(context, JobIntentServiceMain::class.java, JOB_ID, newIntent(context, page))
        }

        private fun newIntent(context: Context, page: Int) =
            Intent(context, JobIntentServiceMain::class.java).apply {
                putExtra(PAGE, page)
            }

    }
}