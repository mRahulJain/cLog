package com.clogg.clog.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clogg.clog.R
import kotlinx.android.synthetic.main.item.view.*

class CardStackAdapter(val nameList : ArrayList<String>) : RecyclerView.Adapter<CardStackAdapter.NameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.item, parent, false)
        return NameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.itemView.helloText.text = nameList[position]
    }

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}