package dev.bong.mobileprogramming.week05

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.R

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val meaningView = itemView.findViewById<TextView>(R.id.meaningView)

        init {
            textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
        holder.meaningView.text = items[position].meaning
        if (!items[position].isOpen) {
            holder.meaningView.visibility = View.GONE
        } else {
            holder.meaningView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}