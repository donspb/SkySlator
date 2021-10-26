package ru.donspb.skyslator.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.donspb.skyslator.R
import ru.donspb.skyslator.model.DataModel

class WordListAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>
) : RecyclerView.Adapter<WordListAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            WordListAdapter.RecyclerItemViewHolder {
        return RecyclerItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.wordlist_item, parent, false) as View)
    }

    override fun onBindViewHolder(holder: WordListAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int = data.size

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            itemView.findViewById<TextView>(R.id.header_wordlist_item).text = data.text
            itemView.findViewById<TextView>(R.id.description_wordlist_item).text =
                data.meanings?.get(0)?.translation?.translation
            itemView.setOnClickListener {
            //TODO Clicklistener
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}