package com.example.mycloset

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CheckroomActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerViewRV: RecyclerView
    private val items = DataBase().itemList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkroom)

        toolbar = findViewById(R.id.toolbar)
        title = ""
        setSupportActionBar(toolbar)
        recyclerViewRV = findViewById(R.id.recyclerViewRV)
        recyclerViewRV.layoutManager = LinearLayoutManager(this)
        recyclerViewRV.adapter = CustomAdapter(items)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) {
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}