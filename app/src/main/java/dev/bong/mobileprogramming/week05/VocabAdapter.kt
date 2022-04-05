package dev.bong.mobileprogramming.week05

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.R

class VocabAdapter(val items: ArrayList<VocabData>) : RecyclerView.Adapter<VocabAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(data: VocabData)
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

        init {
            textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition])
                val textView2 = itemView.findViewById<TextView>(R.id.textView2)
                textView2.text = items[adapterPosition].meaning
                if (textView2.visibility == View.GONE) {
                    textView2.visibility = View.VISIBLE
                } else if (textView2.visibility == View.VISIBLE) {
                    textView2.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}