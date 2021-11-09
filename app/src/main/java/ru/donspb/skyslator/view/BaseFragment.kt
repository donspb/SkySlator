package ru.donspb.skyslator.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentHistoryBinding
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.databinding.LoadingLayoutBinding
import ru.donspb.skyslator.model.data.AppState
import ru.donspb.skyslator.model.data.DataModel
import ru.donspb.skyslator.viewmodel.BaseViewModel

abstract class BaseFragment<T: AppState>: Fragment() {
    private lateinit var bindingLoadLayout: LoadingLayoutBinding
    private lateinit var bindingMainLayout: FragmentMainBinding
    private lateinit var bindingHistoryLayout: FragmentHistoryBinding
    abstract val model: BaseViewModel<T>

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel.isNullOrEmpty())  {
                    showError(Throwable(getString(R.string.error_empty_server_response)))
                } else {
                    showData()
                    setDataToAdapter(dataModel)
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

    private fun showLoading() {
        bindingMainLayout.dataShowerLayer.visibility = View.GONE
        bindingMainLayout.errorScreenLayout.visibility = View.GONE
        bindingLoadLayout.loadingScreenLayout.visibility = View.VISIBLE
    }

    private fun showError(error: Throwable?) {
        bindingMainLayout.dataShowerLayer.visibility = View.GONE
        bindingLoadLayout.loadingScreenLayout.visibility = View.GONE
        bindingMainLayout.errorScreenLayout.visibility = View.VISIBLE
        bindingMainLayout.errorHeaderTv.text = error?.message ?: getString(R.string.error_unknown)
    }

    private fun showData() {
        bindingLoadLayout.loadingScreenLayout.visibility = View.GONE
        bindingMainLayout.errorScreenLayout.visibility = View.GONE
        bindingMainLayout.dataShowerLayer.visibility = View.VISIBLE
    }


    abstract fun setDataToAdapter(data: List<DataModel>)
}