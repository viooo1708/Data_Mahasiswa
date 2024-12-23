package com.viona.data_mahasiswa.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.viona.data_mahasiswa.R
import com.viona.data_mahasiswa.helper.MasiswaDatabaseHelper
import com.viona.data_mahasiswa.model.ModeldataMahasiswa
import com.viona.data_mahasiswa.screen.UpdateDataActivity

class DataAdapter(
    private var datas : List<ModeldataMahasiswa>,
    context : Context
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>(){

    private var db : MasiswaDatabaseHelper = MasiswaDatabaseHelper(context)

    class DataViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val txtItemNama : TextView = itemView.findViewById(R.id.txtItemNama)
        val txtItemNim : TextView = itemView.findViewById(R.id.txtItemNim)
        val txtItemJurusan : TextView = itemView.findViewById(R.id.txtItemJurusan)
        val btnEdit : ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete : ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data,
            parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val mahasiswaData = datas[position]
        holder.txtItemNama.text = mahasiswaData.namaMahasiswa
        holder.txtItemNim.text = mahasiswaData.nim
        holder.txtItemJurusan.text = mahasiswaData.jurusan

        //update

        holder.btnEdit.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateDataActivity::class.java).apply{
                putExtra("data_id", mahasiswaData.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener{
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Confirm")
                setMessage("Do you want continue?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("yes"){
                    dialogInterface, i->
                    db.deleteData(mahasiswaData.id)
                    refreshData(db.getAllData())
                    Toast.makeText(holder.itemView.context,"Data berhasil dihapus",
                        Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                setNeutralButton("No"){
                    dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()
        }

    }

    fun refreshData(newdata : List<ModeldataMahasiswa>){
        datas = newdata
        notifyDataSetChanged()
    }
}