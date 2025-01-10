package com.example.thcaptorsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.thcaptorsapp.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var measurementsTextView: TextView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        measurementsTextView = findViewById(R.id.tvMeasurements)
        logoutBtn = findViewById(R.id.btnLogout)

        // Bouton déconnexion
        logoutBtn.setOnClickListener {
            val prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
            prefs.edit().remove("TOKEN").apply()
            finish()
        }

        fetchMeasurements()
    }

    private fun fetchMeasurements() {
        val prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
        val token = prefs.getString("TOKEN", null)

        if (token.isNullOrEmpty()) {
            measurementsTextView.text = "Veuillez vous reconnecter."
            return
        }

        val retrofit = RetrofitClient.getClient("http://10.0.2.2:3000")
        val apiService = retrofit.create(ApiService::class.java)

        // "Bearer <token>"
        val authHeader = "Bearer $token"

        apiService.getMeasurements(authHeader).enqueue(object : Callback<List<Measurement>> {
            override fun onResponse(
                call: Call<List<Measurement>>,
                response: Response<List<Measurement>>
            ) {
                if (response.isSuccessful) {
                    val measurements = response.body()
                    val sb = StringBuilder()
                    measurements?.forEach { m ->
                        sb.append("${m.timestamp} | Temp: ${m.temperature}°C | Hum: ${m.humidity}%\n")
                    }
                    measurementsTextView.text = sb.toString()
                } else {
                    measurementsTextView.text = "Erreur lors de la récupération des mesures."
                }
            }

            override fun onFailure(call: Call<List<Measurement>>, t: Throwable) {
                measurementsTextView.text = "Échec : ${t.message}"
            }
        })
    }
}
