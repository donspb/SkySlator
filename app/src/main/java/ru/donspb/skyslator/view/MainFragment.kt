package ru.donspb.skyslator.view

import android.os.Bundle
import android.print.PrintAttributes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.presenter.IMainPresenter
import ru.donspb.skyslator.presenter.MainPresenter
import ru.donspb.skyslator.viewmodel.MainViewModel

class MainFragment : Fragment(), MainFragmentView {

    val model: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it) }

//    lateinit var presenter: IMainPresenter<MainFragmentView>

    var adapter: WordListAdapter? = null
    private var binding: FragmentMainBinding? = null

    private val onListItemClickListener: WordListAdapter.OnListItemClickListener =
        object : WordListAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(getContext(), data.text, Toast.LENGTH_SHORT).show()
            }
        }

    fun createPresenter(): IMainPresenter<MainFragmentView> {
        return MainPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        presenter = createPresenter()
        binding?.searchButton?.setOnClickListener {
            onClick(binding?.searchInputField?.text.toString()) }
        binding?.fabBack?.setOnClickListener {
            binding?.searchInputField?.text?.clear()
            showSearch()
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
        model.getData(word).observe(viewLifecycleOwner, observer)
//        if (word.isNotEmpty()) presenter.buttonGoPressed(word)
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

    private fun showError(errorMsg: String?) {
        binding?.dataShowerLayer?.visibility = View.GONE
        binding?.loadingScreenLayout?.visibility = View.GONE
        binding?.errorScreenLayout?.visibility = View.VISIBLE
        binding?.errorHeaderTv?.text = errorMsg ?: getString(R.string.error_unknown)
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
                if (dataModel == null || dataModel.isEmpty())  {
                    showError(getString(R.string.error_empty_server_response))
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

//    override fun onStart() {
//        super.onStart()
////        presenter.attachView(this)
//    }
//
//    override fun onStop() {
//        presenter.detachView(this)
//        super.onStop()
//    }

}