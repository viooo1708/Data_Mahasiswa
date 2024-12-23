package com.viona.data_mahasiswa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.viona.data_mahasiswa.adapter.DataAdapter
import com.viona.data_mahasiswa.databinding.ActivityMainBinding
import com.viona.data_mahasiswa.helper.MasiswaDatabaseHelper
import com.viona.data_mahasiswa.screen.AddDataActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var dataAdapter : DataAdapter
    private lateinit var db : MasiswaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MasiswaDatabaseHelper(this)
        dataAdapter = DataAdapter(db.getAllData(),this)

        binding.dataRecycleview.layoutManager = LinearLayoutManager(this)
        binding.dataRecycleview.adapter = dataAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        val notes = db.getAllData()
        dataAdapter.refreshData(notes)
    }
}