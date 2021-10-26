package ru.donspb.skyslator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.donspb.skyslator.Contract
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.model.DataModel
import ru.donspb.skyslator.presenter.MainPresenter

class MainFragment : MvpAppCompatFragment(), Contract.MainFragmentView {

    val presenter: MainPresenter by moxyPresenter {
        MainPresenter(this)
    }

    var adapter: WordListAdapter? = null
    private var binding: FragmentMainBinding? = null

    private val onListItemClickListener: WordListAdapter.OnListItemClickListener =
        object : WordListAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(getContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.searchButton?.setOnClickListener { onClick(binding?.searchInputField?.text.toString()) }
    }

    fun onClick(word: String) {
        if (word.length > 0) presenter.buttonGoPressed(word)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun showLoading() {
        binding?.searchDialogScreen?.visibility = View.GONE
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.VISIBLE
    }

    override fun showError(error: String) {
        binding?.searchDialogScreen?.visibility = View.GONE
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.VISIBLE
    }

    override fun showData(data: List<DataModel>) {

        if (adapter == null) {
            binding?.wordsRecycler?.layoutManager = LinearLayoutManager(context)
            binding?.wordsRecycler?.adapter = WordListAdapter(onListItemClickListener, data)
        } else {
            adapter!!.setData(data)
        }

        binding?.searchDialogScreen?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
        binding?.dataShowerLayer?.visibility = View.VISIBLE


    }

}