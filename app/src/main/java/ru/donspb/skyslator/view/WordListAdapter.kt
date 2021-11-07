package ru.donspb.skyslator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.donspb.skyslator.databinding.WordlistItemBinding
import ru.donspb.skyslator.model.convertMeaningsToString
import ru.donspb.skyslator.model.data.DataModel

class WordListAdapter(
    private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<WordListAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = listOf()

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
            binding.descriptionWordlistItem.text = convertMeaningsToString(data.meanings!!)
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data)
            }

                //binding.descriptionWordlistItem.text = data.meanings?.get(0)?.translation?.translation
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}