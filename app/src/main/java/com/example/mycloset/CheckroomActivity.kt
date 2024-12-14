package com.example.mycloset

import android.content.Intent
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

    private var adapter: CustomAdapter? = null
    private val dataBase = DBHelper(this)
    private var items: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkroom)

        init()
        recyclerViewRV.layoutManager = LinearLayoutManager(this)

        addDatabaseIfIsEmpty()
    }

    private fun init() {
        toolbar = findViewById(R.id.toolbar)
        title = ""
        setSupportActionBar(toolbar)
        recyclerViewRV = findViewById(R.id.recyclerViewRV)
    }

    override fun onResume(){
        super.onResume()
        items = dataBase.readData()

        adapter = CustomAdapter(items)
        recyclerViewRV.adapter = adapter
        recyclerViewRV.setHasFixedSize(true)

        adapter?.setOnItemClickListener(object :
            CustomAdapter.OnItemClickListener{
            override fun onItemClick(item: Item, position: Int) {
                val intent = Intent(this@CheckroomActivity, DetailsActivity::class.java)
                intent.putExtra("item", item)
                startActivity(intent)
            }
        }
        )
    }

    private fun addDatabaseIfIsEmpty() {
        val itemList = PrimaryDataBase().itemList
        if (dataBase.readData().isEmpty()) {
            for (i in itemList.indices) {
                dataBase.addData(PrimaryDataBase().itemList[i])
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val backMenu = menu?.findItem(R.id.resetDataBase)
        backMenu?.isVisible = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                finishAffinity()
            }
            R.id.resetDataBase -> {
                dataBase.removeAll()
                addDatabaseIfIsEmpty()
                onResume()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}