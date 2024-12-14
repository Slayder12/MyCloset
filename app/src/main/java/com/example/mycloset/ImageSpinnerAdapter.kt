package com.example.mycloset

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class ImageSpinnerAdapter(context: Context, imageList: List<Int>) :
    ArrayAdapter<Int>(context, 0, imageList) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item_layout, parent, false)

        val imageView: ImageView = view.findViewById(R.id.spinnerItemImage)

        val imageResId = getItem(position)!!
        imageView.setImageResource(imageResId)

        return view
    }
}