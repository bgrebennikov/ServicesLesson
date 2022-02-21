package com.bgrebennikov.github.serviceslesson

import android.content.Context
import android.util.Log
import androidx.work.*

class WorkerMain(
    private val context: Context,
    private val params: WorkerParameters
) : Worker(context, params) {


    override fun doWork(): Result {

        val page = params.inputData.getInt(PAGE, 0)

        for (i in 0 until 15){
            Thread.sleep(500L)
            Log.i(TAG, "doWork: $i, page: $page")
        }

        return Result.success()

    }

    companion object{
        private const val PAGE = "page"
        const val WORKER_NAME = "worker_main"

        fun makeRequest(page: Int): OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<WorkerMain>().apply {
                setInputData(workDataOf(PAGE to page))
                setConstraints(
                    Constraints.Builder()
                        .build()
                )
            }
                .build()
        }

    }


}