package com.example.e_montaysta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_montaysta.data.adapters.ToolAdapter
import com.example.e_montaysta.data.datasource.ToolsDataSource
import com.example.e_montaysta.databinding.ActivityToolsBinding

class ToolsActivity : AppCompatActivity() {
    private var binding: ActivityToolsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityToolsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val adapter = ToolAdapter(ToolsDataSource.toolList)
        binding?.toolRv?.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
