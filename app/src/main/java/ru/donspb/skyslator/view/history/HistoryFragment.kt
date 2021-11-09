package ru.donspb.skyslator.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentHistoryBinding
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.view.BaseFragment
import ru.donspb.skyslator.viewmodel.HistoryViewModel

class HistoryFragment() : BaseFragment<AppState>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        initViewModel()
        initView()
    }

    override fun onResume() {
        super.onResume()
        model.getData("")
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryFragment, Observer<AppState> { renderData(it) })
    }

    fun initView() {
        binding.historyRecyclerview.adapter = adapter
    }
}