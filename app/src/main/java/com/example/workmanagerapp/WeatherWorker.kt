package com.example.workmanagerapp

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class WeatherWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams)
{
        override fun doWork(): Result {
            val cityIDs = inputData.getIntArray("cities")
            val API_KEY = "78e056c652997738cf55e5a6f0b7a40b"
            var weatherURL=""
            var d = Data.Builder()

            cityIDs?.let {
                for (c in it){

                weatherURL = "https://api.openweathermap.org/data/2.5/weather?id="+c+"&appid="+API_KEY+"&units=metric";
                var stream = URL(weatherURL).getContent() as InputStream
                val gson = Gson()
                d.putString(c.toString(), gson.fromJson(InputStreamReader(stream), WeatherApi::class.java).main.temp.toString())
            }
            }
            var res =d.build()
            return Result.success(res)
    }
}