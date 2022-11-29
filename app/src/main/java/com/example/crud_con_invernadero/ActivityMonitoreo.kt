package com.example.crud_con_invernadero

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.crud_con_invernadero.databinding.ActivityMonitoreoBinding

class ActivityMonitoreo : AppCompatActivity() {

    private lateinit var binding: ActivityMonitoreoBinding

    @SuppressLint("SetJavaScriptEnabled")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoreoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView1:WebView = findViewById(R.id.webMonitoreo)
        val settings:WebSettings = webView1.settings
        settings.javaScriptEnabled = true
        webView1.webChromeClient = WebChromeClient()
        webView1.loadUrl("https://liyo135513.000webhostapp.com/app/index%20(1).html")

        binding.botonRgistros.setOnClickListener(){
            startActivity(Intent(this,ActivityRegistros::class.java))
        }

        binding.floatingActionButton.setOnClickListener(){
            finish()
            startActivity(intent)
        }

    }
}