package com.example.lab8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val contacts: MutableList<Contact>
) : RecyclerView.Adapter<MyAdapter.ContactViewHolder>() {

    // Use a more descriptive inner class name
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        private val phoneTextView: TextView = itemView.findViewById(R.id.tvPhone)
        private val deleteImageView: ImageView = itemView.findViewById(R.id.imgDelete)

        fun bind(contact: Contact, onDeleteClick: (Contact) -> Unit) {
            nameTextView.text = contact.name
            phoneTextView.text = contact.phone
            deleteImageView.setOnClickListener { onDeleteClick(contact) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position]) { contact ->
            contacts.remove(contact)
            notifyDataSetChanged()
        }
    }
}