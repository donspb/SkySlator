package ru.donspb.skyslator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.WordlistItemBinding
import ru.donspb.skyslator.model.data.DataModel

class WordListAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>
) : RecyclerView.Adapter<WordListAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            WordListAdapter.RecyclerItemViewHolder = RecyclerItemViewHolder(
            WordlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            .apply {
                itemView.setOnClickListener {
                    onListItemClickListener
                }
            }


    override fun onBindViewHolder(holder: WordListAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(val binding: WordlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataModel) {
            binding.headerWordlistItem.text = data.text
            binding.descriptionWordlistItem.text = data.meanings?.get(0)?.translation?.translation
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}