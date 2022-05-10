package dev.bong.mobileprogramming.week10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.databinding.RowParsingBinding

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun OnItemCLick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowParsingBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.newsTitle.setOnClickListener {
                itemClickListener?.OnItemCLick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val view = RowParsingBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.binding.newsTitle.text = items[position].newsTitle
    }

    override fun getItemCount(): Int {
        return items.size
    }

}