package com.example.hw7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    items: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, items) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // Use LayoutInflater for more efficient view inflation
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        // Use null-safe call and let for better null handling
        getItem(position)?.let { item ->
            view.findViewById<ImageView>(R.id.imgPhoto)
                .setImageResource(item.photo)

            view.findViewById<TextView>(R.id.tvMsg).text = when (layout) {
                R.layout.adapter_vertical -> item.name
                else -> "${item.name}: ${item.price}元"
            }
        }

        return view
    }
}