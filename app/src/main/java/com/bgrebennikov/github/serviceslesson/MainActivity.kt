package com.bgrebennikov.github.serviceslesson

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.bgrebennikov.github.serviceslesson.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.service.setOnClickListener {

        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, ForegroundService.newInstance(this))
        }

        binding.stopForegroundService.setOnClickListener {
            stopService(ForegroundService.newInstance(this))
        }

        binding.jobScheduler.setOnClickListener {
            val componentName = ComponentName(this, JobServiceMain::class.java)
            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val jobInfo = JobInfo.Builder(JobServiceMain.JOB_ID, componentName)
                .build()


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                jobScheduler.enqueue(jobInfo, JobWorkItem(JobServiceMain.newIntent(page++)))
            } else{
                startService(IntentServiceMain.newIntent(this, page++))
            }
        }

        binding.jobIntentService.setOnClickListener {
            JobIntentServiceMain.enqueue(this, page++)
        }

        binding.workManager.setOnClickListener {
            val workManager = WorkManager.getInstance(applicationContext)
            workManager.enqueueUniqueWork(
                WorkerMain.WORKER_NAME,
                ExistingWorkPolicy.APPEND,
                WorkerMain.makeRequest(page++)
            )
        }

    }


    companion object {
        const val CHANNEL_ID = "ch_id"
        const val CHANNEL_NAME = "ch_name"
    }

}