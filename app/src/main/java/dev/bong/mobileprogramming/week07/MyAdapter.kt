package dev.bong.mobileprogramming.week07

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.databinding.RowAppBinding

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(data: MyData)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: RowAppBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                itemClickListener?.OnItemClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            appclass.text = items[position].appclass
            applabel.text = items[position].applabel
            imageView.setImageDrawable(items[position].appicon)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}