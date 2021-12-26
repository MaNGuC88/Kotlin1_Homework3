package com.example.kotlin1_homework3.ui.selected_images

import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.kotlin1_homework3.base.BaseActivity
import com.example.kotlin1_homework3.base.BaseViewModel
import com.example.kotlin1_homework3.databinding.ActivitySelectedImagesBinding

class SelectedImagesActivity : BaseActivity<BaseViewModel, ActivitySelectedImagesBinding>() {

    private lateinit var adapter: SelectedImagesAdapter
    private var list: MutableList<String> = mutableListOf()

    override fun initViewModel() {
    }

    override fun initListeners() {
    }

    override fun initView() {
        supportActionBar?.title = "Выбранные фотографии"
        list = intent.getStringArrayListExtra("selected")!!

        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = SelectedImagesAdapter(this, list)
        binding.recyclerSelected.adapter = adapter
        binding.recyclerSelected.setHasFixedSize(true)
        binding.recyclerSelected.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun inflateVB(inflater: LayoutInflater): ActivitySelectedImagesBinding {
        return ActivitySelectedImagesBinding.inflate(inflater)
    }
}