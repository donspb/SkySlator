package ru.donspb.skyslator.view.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.donspb.skyslator.Consts
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.model.convertMeaningsToString
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.view.BaseFragment
import ru.donspb.skyslator.view.wordview.WordFragment
import ru.donspb.skyslator.viewmodel.MainViewModel


class MainFragment : BaseFragment<AppState>() {

    override lateinit var model: MainViewModel
//    private val observer = Observer<AppState> { renderData(it) }
    private val adapter by lazy { WordListAdapter(onListItemClickListener) }
    private var binding: FragmentMainBinding? = null

    private val onListItemClickListener: WordListAdapter.OnListItemClickListener =
        object : WordListAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.container, WordFragment.newInstance(Bundle().apply {
                            putString(Consts.WORD_HEADER_KEY, data.text)
                            putString(Consts.WORD_DESCR_KEY, convertMeaningsToString(data.meanings!!))
                            putString(Consts.WORD_IMAGEURL_KEY, data.meanings[0].imageUrl)
                        }))
                        .addToBackStack("")
                        .commit()
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding?.wordsRecycler?.layoutManager = LinearLayoutManager(context)
        binding?.wordsRecycler?.adapter = adapter
        binding?.searchButton?.setOnClickListener {
            onClick(binding?.searchInputField?.text.toString()) }
        binding?.fabBack?.setOnClickListener {
            binding?.searchInputField?.text?.clear()
            showSearch()
            adapter.setData(listOf())
        }
    }

    private fun showSearch() {
        binding?.searchDialogScreen?.visibility = View.VISIBLE
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
    }

    private fun onClick(word: String) {
        binding?.searchDialogScreen?.visibility = View.GONE
        model.getData(word)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }

//    private fun showLoading() {
//        binding?.dataShowerLayer?.visibility = View.GONE
//        binding?.errorScreenLayout?.visibility = View.GONE
//        binding?.loadingScreenLayout?.visibility = View.VISIBLE
//    }
//
//    private fun showError(error: Throwable?) {
//        binding?.dataShowerLayer?.visibility = View.GONE
//        binding?.loadingScreenLayout?.visibility = View.GONE
//        binding?.errorScreenLayout?.visibility = View.VISIBLE
//        binding?.errorHeaderTv?.text = error?.message ?: getString(R.string.error_unknown)
//    }
//
//    private fun showData() {
//        binding?.loadingScreenLayout?.visibility = View.GONE
//        binding?.errorScreenLayout?.visibility = View.GONE
//        binding?.dataShowerLayer?.visibility = View.VISIBLE
//    }

//    override fun renderData(appState: AppState) {
//        when (appState) {
//            is AppState.Success -> {
//                val dataModel = appState.data
//                if (dataModel.isNullOrEmpty())  {
//                    showError(Throwable(getString(R.string.error_empty_server_response)))
//                } else {
//                    showData()
//                    adapter.setData(dataModel)
//                }
//            }
//            is AppState.Error -> {
//                showError(appState.error)
//            }
//            is AppState.Loading -> {
//                showLoading()
//            }
//        }
//    }

    private fun initViewModel() {
        if (binding?.wordsRecycler?.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialized first")
        }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        TODO("Not yet implemented")
    }

}