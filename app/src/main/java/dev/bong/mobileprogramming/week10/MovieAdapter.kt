package dev.bong.mobileprogramming.week10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.bong.mobileprogramming.databinding.RowMovieDataBinding

class MovieAdapter(val items: ArrayList<MovieData>) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun OnItemCLick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowMovieDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.title.setOnClickListener {
                itemClickListener?.OnItemCLick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MyViewHolder {
        val view = RowMovieDataBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MyViewHolder, position: Int) {
        holder.binding.ranking.text = items[position].ranking.toString()
        holder.binding.title.text = items[position].title
        holder.binding.rate.text = items[position].rate
    }

    override fun getItemCount(): Int {
        return items.size
    }

}