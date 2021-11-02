package ru.donspb.skyslator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.viewmodel.MainViewModel
import javax.inject.Inject

class MainFragment : Fragment(), MainFragmentView {

    lateinit var model: MainViewModel
//    private val observer = Observer<AppState> { renderData(it) }
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
    ): View = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding?.searchButton?.setOnClickListener {
            onClick(binding?.searchInputField?.text.toString()) }
        binding?.fabBack?.setOnClickListener {
            binding?.searchInputField?.text?.clear()
            showSearch()
            binding?.wordsRecycler?.adapter = null
        }
    }

    private fun showSearch() {
        binding?.searchDialogScreen?.visibility = View.VISIBLE
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.GONE
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

    private fun showLoading() {
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.VISIBLE
    }

    private fun showError(error: Throwable?) {
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.VISIBLE
        binding?.errorHeaderTv?.text = error?.message ?: getString(R.string.error_unknown)
    }

    private fun showData() {
        binding?.loadingScreenLayout?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.GONE
        binding?.dataShowerLayer?.visibility = View.VISIBLE
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel.isNullOrEmpty())  {
                    showError(Throwable(getString(R.string.error_empty_server_response)))
                } else {
                    showData()
                    if (adapter == null) {
                        binding?.wordsRecycler?.layoutManager = LinearLayoutManager(context)
                        binding?.wordsRecycler?.adapter = WordListAdapter(
                            onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }
            is AppState.Error -> {
                showError(appState.error)
            }
            is AppState.Loading -> {
                showLoading()
            }
        }
    }

    private fun initViewModel() {
        if (binding?.wordsRecycler?.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialized first")
        }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
    }
}