package com.astery.vtb.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astery.vtb.R
import com.astery.vtb.model.LifeItemHolder
import java.util.*

class LifeItemAdapter(private var units: ArrayList<LifeItemHolder>?, private var context:Context) :
    RecyclerView.Adapter<LifeItemAdapter.ViewHolder>() {
    private var blockListener: BlockListener? = null

    fun setUnits(units: ArrayList<LifeItemHolder>?) {
        this.units = units
        notifyDataSetChanged()
    }

    fun setBlockListener(block_listener: BlockListener?) {
        blockListener = block_listener
    }

    interface BlockListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.life_item_unit, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unit = units!![position]
    }

    override fun getItemCount(): Int {
        return if (units == null) 0 else units!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text)
        val icon: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener { v: View? ->
                if (blockListener != null) {
                    blockListener!!.onClick(adapterPosition)
                }
            }
        }
    }
}