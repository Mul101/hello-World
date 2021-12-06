package com.codemul.pabmul.helloworld

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val context: Context, private val list: ArrayList<EsportEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var poster: ImageView

        init {
            poster = itemView.findViewById(R.id.iv_poster_event) // Initialize your All views prensent in list items
        }

        internal fun bind(position: Int) {
            // This method will be called anytime a list item is created or update its data
            //Do your stuff here
            poster.setImageResource(list[position].posterImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.featured_event_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}