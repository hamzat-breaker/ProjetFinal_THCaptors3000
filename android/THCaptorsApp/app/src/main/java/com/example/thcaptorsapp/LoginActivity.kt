package com.example.thcaptorsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.thcaptorsapp.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var loginBtn: Button
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEdit = findViewById(R.id.etEmail)
        passwordEdit = findViewById(R.id.etPassword)
        loginBtn = findViewById(R.id.btnLogin)
        statusTextView = findViewById(R.id.tvStatus)

        loginBtn.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()
            doLogin(email, password)
        }
    }

    private fun doLogin(email: String, password: String) {
        val retrofit = RetrofitClient.getClient("http://10.0.2.2:3000") 
        val apiService = retrofit.create(ApiService::class.java)

        val request = LoginRequest(email, password)
        apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        // Stocker le token localement (SharedPreferences)
                        val prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE)
                        prefs.edit().putString("TOKEN", token).apply()

                        // Rediriger vers DashboardActivity
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    statusTextView.text = "Identifiants invalides ou erreur du serveur."
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                statusTextView.text = "Échec : ${t.message}"
            }
        })
    }
}
