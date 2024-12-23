package com.viona.data_mahasiswa.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viona.data_mahasiswa.R
import com.viona.data_mahasiswa.databinding.ActivityUpdateDataBinding
import com.viona.data_mahasiswa.helper.MasiswaDatabaseHelper
import com.viona.data_mahasiswa.model.ModeldataMahasiswa

class UpdateDataActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateDataBinding
    private lateinit var db : MasiswaDatabaseHelper
    private var dataId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MasiswaDatabaseHelper(this)

        dataId = intent.getIntExtra("data_id",-1)

        if (dataId == -1){
            finish()
            return
        }

        val data = db.getDataById(dataId)
        binding.etEditNama.setText(data.namaMahasiswa)
        binding.etEditNim.setText(data.nim)
        binding.etEditJurusan.setText(data.jurusan)

        binding.imgEdit.setOnClickListener{
            val newName = binding.etEditNama.text.toString()
            val newNim = binding.etEditNim.text.toString()
            val newJurusan = binding.etEditJurusan.text.toString()

            val updateData = ModeldataMahasiswa(dataId, newName, newNim, newJurusan)
            db.updateData(updateData)
            finish()
            Toast.makeText(this,"Change Save", Toast.LENGTH_SHORT).show()
        }

    }
}