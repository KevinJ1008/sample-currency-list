package com.kevinj1008.samplecurrencylist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.basecore.base.BaseFragment
import com.kevinj1008.samplecurrencylist.databinding.FragmentListResultBinding
import com.kevinj1008.samplecurrencylist.epoxy.CurrencyInfoEpoxyController
import com.kevinj1008.samplecurrencylist.interfaces.CustomFragmentManager
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import com.kevinj1008.widget.CustomItemDecoration
import com.kevinj1008.widget.EmptyView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CurrencyListFragment : BaseFragment<FragmentListResultBinding>() {

    private val viewModel: CurrencyViewModel by sharedViewModel()
    private val epoxyController = CurrencyInfoEpoxyController()
    private var dividerDecoration: CustomItemDecoration? = null
    private var manager: CustomFragmentManager? = null
    private var layoutEmpty: EmptyView? = null

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentListResultBinding = FragmentListResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        registerObserver()
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        manager?.onShowSortingButton(false)
        manager = null
        super.onDestroyView()
    }

    fun setFragmentManager(manager: CustomFragmentManager?) {
        this.manager = manager
    }

    private fun initViews() {
        binding?.recyclerview?.setController(epoxyController)
        dividerDecoration = CustomItemDecoration(requireContext())
        dividerDecoration?.apply {
            binding?.recyclerview?.removeItemDecoration(this)
            binding?.recyclerview?.addItemDecoration(this)
        }
        manager?.onShowSortingButton(true)
    }

    private fun registerObserver() {
        viewModel.currencyList.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                epoxyController.setCurrencyList(this)
                viewModel.setLoading(isLoading = false)
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                binding?.layoutLoadingContainer?.isVisible = this
                manager?.onDataLoading(this)
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                viewModel.setLoading(isLoading = false)
                manager?.onShowSortingButton(isVisible = false)
                showErrorView()
            }
        })
        //ClickListener which hooked from item view in Recyclerview to this parent, use liveData to
        // achieve could prevent write new wrapper class
        epoxyController.clickEvent.observe(viewLifecycleOwner, {
            when (val clickEvent = it.getContentIfNotHandled()) {
                is CurrencyInfoEpoxyController.ClickEvent.CurrencyEvent -> {
                    Snackbar.make(requireView(), "Click on position: ${clickEvent.position}, " +
                            "name is: ${clickEvent.name}, " +
                            "symbol is: ${clickEvent.symbol}",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showErrorView() {
        if (layoutEmpty == null) {
            layoutEmpty = binding?.stubEmpty?.inflate() as? EmptyView?
            layoutEmpty?.apply {
                setOnRetryClickListener {
                    layoutEmpty?.isVisible = false
                    manager?.onRetryLoadData()
                }
            }
        }
        layoutEmpty?.isVisible = true
    }
}