package com.example.kotlinmvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivityAdapter(
    private val items: List<String>,
    private val onItemClick: (item: String) -> Unit = {}
):
        RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var contentTitle = itemView.findViewById(R.id.contentText) as TextView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_recycler_view, p0, false)
        val vh = ViewHolder(view)
        view.setOnClickListener {
            onItemClick(items[vh.adapterPosition])
        }
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contentTitle.text = items[position]
    }

    override fun getItemCount() = items.size
}