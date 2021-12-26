package com.example.kotlin1_homework3.ui.gallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.kotlin1_homework3.base.BaseActivity
import com.example.kotlin1_homework3.base.BaseViewModel
import com.example.kotlin1_homework3.databinding.ActivityGalleryBinding
import androidx.core.app.ActivityCompat
import android.annotation.SuppressLint
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlin1_homework3.base.OnItemClick
import com.example.kotlin1_homework3.ui.selected_images.SelectedImagesActivity
import android.os.Parcelable
import android.view.View
import java.io.Serializable

class GalleryActivity : BaseActivity<BaseViewModel, ActivityGalleryBinding>() {

    private lateinit var adapter: GalleryAdapter
    private var list: MutableList<String> = mutableListOf()
    private var selectedList: MutableList<String> = mutableListOf()
    private val permissions: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun initListeners() {
        adapter.setOnItemClick(object : OnItemClick {
            override fun onItemClick(position: Int) {
                binding.rl.visibility = View.VISIBLE
                val selectedUri: String = adapter.getItem(position)
                selectedList.add(selectedUri)
            }
        })

        binding.btnReady.setOnClickListener {
            sendSelectedList()
        }
    }

    private fun sendSelectedList() {
        val intent = Intent(this, SelectedImagesActivity::class.java)
        if (selectedList.isNotEmpty()) {
            intent.putExtra("selected", selectedList as Serializable?)
        }
        startActivity(intent)
    }

    override fun initView() {
        supportActionBar?.title = "Выбор фотографий"
        binding.rl.visibility = View.GONE

        setupAdapter()
        getPermissionForGallery()
    }

    private fun setupAdapter() {
        adapter = GalleryAdapter(this, list)
        binding.recyclerGallery.adapter = adapter
        binding.recyclerGallery.setHasFixedSize(true)
        binding.recyclerGallery.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))
    }

    private fun getPermissionForGallery() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, 1)
                }
            }
            else -> {
                pickImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> for (result in grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                    return
                } else {
                    ActivityCompat.requestPermissions(this@GalleryActivity,
                        permissions, 1)
                }
            }
        }
    }

    private fun pickImage() {
        Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
            openGallery.launch(this)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private var openGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result != null) {
                    val count: Int = result.data!!.clipData!!.itemCount
                    for (i in 0 until count) {
                        list.add(result.data!!.clipData!!.getItemAt(i).uri.toString())
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }

    override fun initViewModel() {
    }

    override fun inflateVB(inflater: LayoutInflater): ActivityGalleryBinding {
        return ActivityGalleryBinding.inflate(inflater)
    }

}

