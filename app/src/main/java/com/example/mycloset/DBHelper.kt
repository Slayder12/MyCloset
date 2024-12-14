package com.example.mycloset

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "ITEM_DATABASE"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "item_table"
        val KEY_ID = "id"
        val KEY_NAME = "name"
        val KEY_DESCRIPTION = "description"
        val KEY_IMAGE = "image"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("  +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NAME + " TEXT, " +
                KEY_DESCRIPTION + " TEXT, " +
                KEY_IMAGE + " INTEGER" + ")")
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addData(item: Item){
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_ID, item.id)
        contentValues.put(KEY_NAME, item.name)
        contentValues.put(KEY_DESCRIPTION, item.description)
        contentValues.put(KEY_IMAGE, item.image)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }


    @SuppressLint("Range", "Recycle")
    fun readData(): MutableList<Item> {

        val itemList: MutableList<Item> = mutableListOf()

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.execSQL(selectQuery)
            return itemList
        }
        var itemId: Int
        var itemName: String
        var itemDescription: String
        var itemImage: Int

        if (cursor.moveToFirst()){
            do {
                itemId = cursor.getInt(cursor.getColumnIndex("id"))
                itemName = cursor.getString(cursor.getColumnIndex("name"))
                itemDescription = cursor.getString(cursor.getColumnIndex("description"))
                itemImage = cursor.getInt(cursor.getColumnIndex("image"))

                val item = Item(itemId, itemName, itemDescription, itemImage)
                itemList.add(item)

            } while (cursor.moveToNext())
        }
        return itemList
    }

    fun updateData(item: Item){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, item.id)
        contentValues.put(KEY_NAME, item.name)
        contentValues.put(KEY_DESCRIPTION, item.description)
        contentValues.put(KEY_IMAGE, item.image)

        db.update(TABLE_NAME, contentValues,"id=" + item.id, null)
        db.close()
    }

    fun deleteData(item: Item){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, item.id)
        db.delete(TABLE_NAME,"id=" + item.id, null)
        db.close()
    }

    fun removeAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

}