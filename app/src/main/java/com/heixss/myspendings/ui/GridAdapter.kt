package com.heixss.myspendings.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heixss.myspendings.R


class GridAdapter internal constructor(
    context: Context?,
    data: List<String>
) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    private var mData: List<String>
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_month, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.myTextView.text = mData[position]
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView
        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            myTextView = itemView.findViewById(R.id.tvMonth)
            itemView.setOnClickListener(this)
        }
    }

    fun setItems(data: List<String>) {
        this.mData = data
        notifyDataSetChanged()
    }

    fun getItems(): List<String>{
        return mData;
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): String {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        mData = data
    }
}