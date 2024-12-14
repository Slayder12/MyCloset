package com.example.mycloset

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailsActivity : AppCompatActivity() {

    private val dataBase = DBHelper(this)
    var item: Item? = null
    var selectedImageResId: Int? = null

    private lateinit var toolbar: Toolbar
    private lateinit var displayNameTV: TextView
    private lateinit var displayDescriptionTV: TextView
    private lateinit var displayImageIV: ImageView
    private lateinit var layoutLL: LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        layoutLL = findViewById(R.id.layoutLL)
        toolbar = findViewById(R.id.toolbar)
        displayNameTV = findViewById(R.id.displayNameTV)
        displayDescriptionTV = findViewById(R.id.displayDescriptionTV)
        displayImageIV = findViewById(R.id.displayImageTV)


        title = ""
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }

        if (intent.hasExtra("item")){
            item = intent.getSerializableExtra("item") as Item
        }

        if(item != null) {

            displayNameTV.text = item?.name
            displayDescriptionTV.text = item?.description
            displayImageIV.setImageResource(item!!.image)
        }

        layoutLL.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.update_dialog, null)
            dialog.setView(dialogView)

            val itemImageSpinner: Spinner = dialogView.findViewById(R.id.updateImageSP)

            selectedImageResId = item?.image

            val itemImageAdapter = ImageSpinnerAdapter(this, PrimaryDataBase().imageList)
            itemImageSpinner.adapter = itemImageAdapter
            itemImageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            itemImageSpinner.adapter = itemImageAdapter

            val itemSelectedListener: OnItemSelectedListener =
                object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedImageResId = PrimaryDataBase().imageList[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            itemImageSpinner.onItemSelectedListener = itemSelectedListener

            val updateNameET: EditText = dialogView.findViewById(R.id.updateNameET)
            val updateDescriptionET: EditText = dialogView.findViewById(R.id.updateDescriptionET)

            dialog.setTitle(getString(R.string.update_data_text))
            dialog.setMessage(getString(R.string.update_data_below_text))
            dialog.setPositiveButton(getString(R.string.update_text)) { _, _ ->

                displayNameTV.text = updateNameET.text.toString()
                displayDescriptionTV.text = updateDescriptionET.text.toString()
                displayImageIV.setImageResource(selectedImageResId!!)

                val item = Item(
                    item!!.id,
                    displayNameTV.text.toString(),
                    displayDescriptionTV.text.toString(),
                    selectedImageResId!!
                )
                dataBase.updateData(item)
            }
            dialog.setNegativeButton(getString(R.string.cancelation_text)) { _, _ ->
            }
            dialog.create().show()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenu -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

