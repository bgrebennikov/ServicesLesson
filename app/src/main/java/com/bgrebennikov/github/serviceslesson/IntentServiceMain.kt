package com.bgrebennikov.github.serviceslesson

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class IntentServiceMain: IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(true)
    }


    override fun onHandleIntent(intent: Intent?) {

        val page = intent?.getIntExtra(PAGE, 0) ?: 0

        for(i in 0 until 100){
            Thread.sleep(1000L)
            Log.i(TAG, "timer: $i, page:$page")
        }
    }

    companion object{
        private const val NAME = "intent_service"
        const val TAG = "MESSAGE"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent{
            return Intent(context, IntentServiceMain::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}