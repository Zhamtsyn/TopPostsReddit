package com.example.toppostsreddit.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toppostsreddit.databinding.ActivityMainBinding
import com.example.toppostsreddit.main.TopPostsAdapter
import com.example.toppostsreddit.main.TopPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var topPostsAdapter: TopPostsAdapter
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val viewModel: TopPostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        loadingData()
        checkPermission()
    }

    private fun permissionListener() {
        pLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {  }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionListener()
            pLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    }

    private fun loadingData() {
        viewModel.topPosts.observe(this@MainActivity) { response->

            topPostsAdapter.differ.submitList(response.data)
        }
    }

    private fun initRecyclerView() {
        binding.rvPosts.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        topPostsAdapter = TopPostsAdapter(onClickListener = { url -> handleOnImageClick(url) },
            onTextViewClickListener = { url: String -> handleOnTextViewClickListener(url) })
        binding.rvPosts.adapter = topPostsAdapter
    }

    private fun handleOnTextViewClickListener(url: String) {
        val outStream: FileOutputStream?

        try {
            val sdCard = Environment.getExternalStorageDirectory()
            val dir = File("$sdCard/Pictures/Top posts Reddit")
            dir.mkdirs()
            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(dir, fileName)
            outStream = FileOutputStream(outFile)

            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                try {
                    val `in` = java.net.URL(url).openStream()
                    val bitmap = BitmapFactory.decodeStream(`in`)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext, "Image downloaded successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    outStream.flush()
                    outStream.close()

                    val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    val contentUri = Uri.fromFile(outFile)
                    scanIntent.data = contentUri
                    sendBroadcast(scanIntent)

                } catch (e: Throwable) {
                    withContext(Dispatchers.Main){
                        onError(e)
                    }
                }
            }

        } catch (e: Throwable) {
            onError(e)
        }

    }

    private fun onError(e:Throwable){
        Toast.makeText(
            applicationContext, "Something went wrong...", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }

    private fun handleOnImageClick(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }
}