package com.kevinj1008.samplecurrencylist.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.basecore.base.BaseFragment
import com.kevinj1008.samplecurrencylist.databinding.FragmentListResultBinding
import com.kevinj1008.samplecurrencylist.epoxy.CurrencyInfoEpoxyController
import com.kevinj1008.samplecurrencylist.interfaces.CustomFragmentManager
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import com.kevinj1008.widget.CustomItemDecoration
import com.kevinj1008.widget.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

//TODO: handle sorting and restore case
class CurrencyListFragment : BaseFragment<FragmentListResultBinding>() {

    private val viewModel: CurrencyViewModel by sharedViewModel()
    private val epoxyController = CurrencyInfoEpoxyController()
    private var dividerDecoration: CustomItemDecoration? = null
    private var manager: CustomFragmentManager? = null

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
        super.onDestroyView()
        manager = null
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
            epoxyController.setCurrencyList(it)
        })
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
}