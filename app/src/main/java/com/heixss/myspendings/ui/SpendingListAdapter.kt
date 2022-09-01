package com.heixss.myspendings.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.VMSpending


class SpendingListAdapter internal constructor(
    private val context: Context,
    private var mData: List<VMSpending>
) :
    RecyclerView.Adapter<SpendingListAdapter.ViewHolder>() {
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_vm_spending, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each cell
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val spending = mData[position]
        holder.tvCategory.text = spending.category.name
        holder.tvValue.text = spending.spending.value.toString()
        holder.tvDate.text =
            "${spending.spending.day}/${spending.spending.month + 1}/${spending.spending.year}"
        holder.tvDesc.text = spending.spending.description
        holder.bind(spending)
    }

    // total number of cells
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvValue: TextView = itemView.findViewById(R.id.tvValue)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val ivRemove: ImageView = itemView.findViewById(R.id.ivRemove)
        private lateinit var vmSpending: VMSpending
        override fun onClick(view: View?) {
            if(view?.id == R.id.ivRemove){
                if (mClickListener != null) mClickListener!!.onItemRemove(view, vmSpending)
                return
            }
            if (mClickListener != null) mClickListener!!.onItemClick(view, absoluteAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
            ivRemove.setOnClickListener(this)
        }

        fun bind(vmSpending: VMSpending){
            this.vmSpending = vmSpending
        }
    }

    fun setItems(mData: List<VMSpending>){
        this.mData = mData
        notifyDataSetChanged()
    }
    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemRemove(view: View?, vmSpending: VMSpending)
    }
}
