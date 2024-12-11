package com.example.mycloset

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var startBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        title = ""
        setSupportActionBar(toolbar)
        startBTN = findViewById(R.id.startBTN)

        startBTN.setOnClickListener{
            val intent = Intent(this, CheckroomActivity::class.java)
            startActivity(intent)
        }
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