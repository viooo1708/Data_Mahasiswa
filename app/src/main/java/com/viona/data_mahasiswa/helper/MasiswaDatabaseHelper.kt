package com.viona.data_mahasiswa.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.viona.data_mahasiswa.model.ModeldataMahasiswa

class  MasiswaDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "data.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "datamahasiswa"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAMA = "nama"
        private const val COLUMN_NIM = "nim"
        private const val COLUMN_JURUSAN = "jurusan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAMA TEXT, $COLUMN_NIM TEXT, $COLUMN_JURUSAN TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(data: ModeldataMahasiswa) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA, data.namaMahasiswa)
            put(COLUMN_NIM, data.nim)
            put(COLUMN_JURUSAN, data.jurusan)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllData() : List<ModeldataMahasiswa> {
        val dataList = mutableListOf<ModeldataMahasiswa>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
            val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
            val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))

            Log.d("DatabaseHelper","fatched note-id $id, title $nama")

            val data = ModeldataMahasiswa(id, nama, nim, jurusan)
            dataList.add(data)
        }
        cursor.close()
        db.close()
        return dataList
    }

    fun updateData(data : ModeldataMahasiswa){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAMA,data.namaMahasiswa)
            put(COLUMN_NIM,data.nim)
            put(COLUMN_JURUSAN,data.jurusan)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(data.id
            .toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getDataById(data : Int) : ModeldataMahasiswa{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $data"
        val cursor = db.rawQuery(query,null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val nama = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
        val nim = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIM))
        val jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JURUSAN))

        cursor.close()
        db.close()
        return ModeldataMahasiswa(id, nama, nim, jurusan)
    }

    fun deleteData(dataId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(dataId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }


}
