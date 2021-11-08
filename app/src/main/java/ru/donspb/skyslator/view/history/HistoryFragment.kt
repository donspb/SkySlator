package ru.donspb.skyslator.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.donspb.skyslator.R
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.view.BaseFragment
import ru.donspb.skyslator.viewmodel.BaseViewModel
import ru.donspb.skyslator.viewmodel.HistoryViewModel

class HistoryFragment() : BaseFragment<AppState>() {

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        TODO("Not yet implemented")
    }
}