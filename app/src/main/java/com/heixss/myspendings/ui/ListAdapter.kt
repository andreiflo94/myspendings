package com.heixss.myspendings.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.TotalCategorySpendings


class ListAdapter internal constructor(
    private val context: Context,
    private var mData: List<TotalCategorySpendings>
) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_spending, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val spending = mData[position]
        holder.tvCategory.text = spending.category.name
        holder.tvValue.text = spending.totalSum.toString()
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvValue: TextView = itemView.findViewById(R.id.tvValue)
        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    fun getItem(position: Int): TotalCategorySpendings{
        return mData[position]
    }

    fun setList(mData: List<TotalCategorySpendings>){
        this.mData = mData
        notifyDataSetChanged()
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
