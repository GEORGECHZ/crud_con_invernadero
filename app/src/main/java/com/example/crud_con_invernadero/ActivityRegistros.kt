package com.example.crud_con_invernadero

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.crud_con_invernadero.databinding.ActivityRegistrosBinding

class ActivityRegistros : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrosBinding

    @SuppressLint("SetJavaScriptEnabled")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView2: WebView = findViewById(R.id.webRegistros)
        val settings: WebSettings = webView2.settings
        settings.javaScriptEnabled = true
        webView2.webChromeClient = WebChromeClient()
        webView2.loadUrl("https://liyo135513.000webhostapp.com/app/index2.html")

    }
}