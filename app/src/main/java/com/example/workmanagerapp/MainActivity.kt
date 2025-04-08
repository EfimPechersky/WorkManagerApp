package com.example.workmanagerapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val citiesData = workDataOf("cities" to intArrayOf(524901, 5128581,2643743))
        val workRequest = OneTimeWorkRequest.Builder(WeatherWorker::class.java)
            .setInputData(citiesData)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, androidx.lifecycle.Observer {
                if (it?.state==WorkInfo.State.SUCCEEDED){
                    findViewById<TextView>(R.id.moscow).text="Москва: "+it.outputData.getString("524901")
                    findViewById<TextView>(R.id.ny).text="Нью-Йорк: "+it.outputData.getString("5128581")
                    findViewById<TextView>(R.id.london).text="Лондон: "+it.outputData.getString("2643743")
                }
            })

    }
}