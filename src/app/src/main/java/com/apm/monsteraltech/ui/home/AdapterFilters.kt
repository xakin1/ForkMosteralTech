package com.apm.monsteraltech.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.apm.monsteraltech.R

class AdapterFilters(private val filterList: ArrayList<Filter>) : RecyclerView.Adapter<AdapterFilters.ViewHolder>() {

    private lateinit var listener: OnItemClickedListener
    private val buttonList = ArrayList<Button>()

    interface OnItemClickedListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnFilter: Button = itemView.findViewById(R.id.button_filter)

        fun setData(filter: Filter) {
            btnFilter.text = filter.filterName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_filter, parent, false)
        val viewHolder = ViewHolder(view)
        buttonList.add(viewHolder.btnFilter)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(filterList[position])
        holder.btnFilter.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    fun getButton(position: Int): Button {
        return buttonList[position]
    }

    fun getFilter(position: Int): Filter {
        return filterList[position]
    }
}
