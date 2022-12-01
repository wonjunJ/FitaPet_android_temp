package com.example.fitapet.wonjune

import android.Manifest
import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.webkit.*
import androidx.core.app.ActivityCompat
import com.example.fitapet.R

class DownActivity : AppCompatActivity() {
    lateinit var webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_view)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MODE_PRIVATE)

        var intent = intent
        var downtitle = intent.getStringExtra("title")

        webView = findViewById<WebView>(R.id.downview)
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true

        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            //webDownload = DownloadListener(WebViewClient())
        }
        webView.loadUrl("http://118.45.212.21:8000/pets/download/?position="+downtitle)

        //웹뷰에서 다운로드 구현을 위한 코드
        webView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            val filename = URLUtil.guessFileName(url, contentDisposition, mimetype)
            val cookies = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookies)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file..")
            request.setTitle(filename)
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
            val dManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dManager.enqueue(request)
        })
        finish()

    }
}


