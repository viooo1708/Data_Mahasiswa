package com.viona.data_mahasiswa.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viona.data_mahasiswa.R
import com.viona.data_mahasiswa.databinding.ActivityAddDataBinding
import com.viona.data_mahasiswa.databinding.ActivityMainBinding
import com.viona.data_mahasiswa.helper.MasiswaDatabaseHelper
import com.viona.data_mahasiswa.model.ModeldataMahasiswa

class AddDataActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddDataBinding
    private lateinit var db: MasiswaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MasiswaDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val nama = binding.namaEditText.text.toString()
            val nim = binding.nimEditText.text.toString()
            val jurusan = binding.jurusanEditText.text.toString()

            val data = ModeldataMahasiswa(0, nama,nim,jurusan)

            db.insertData(data)
            finish()
            Toast.makeText(this, "Data disimpan", Toast.LENGTH_SHORT).show()
        }
    }
}