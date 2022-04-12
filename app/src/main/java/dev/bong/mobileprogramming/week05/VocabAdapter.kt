package dev.bong.mobileprogramming.week05

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.RowBinding

class VocabAdapter(val items: ArrayList<VocabData>) : RecyclerView.Adapter<VocabAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(data: VocabData, position: Int)
    }

    fun moveItem(oldPos: Int, newPos: Int) {
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = items[position].word
        holder.binding.meaningView.text = items[position].meaning
        if (!items[position].isOpen) {
            holder.binding.meaningView.visibility = View.GONE
        } else {
            holder.binding.meaningView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}