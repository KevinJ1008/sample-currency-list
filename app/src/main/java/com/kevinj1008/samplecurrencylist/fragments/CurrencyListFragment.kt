package com.kevinj1008.samplecurrencylist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.basecore.base.BaseFragment
import com.kevinj1008.samplecurrencylist.databinding.FragmentListResultBinding
import com.kevinj1008.samplecurrencylist.epoxy.CurrencyInfoEpoxyController
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CurrencyListFragment : BaseFragment<FragmentListResultBinding>() {

    private val viewModel: CurrencyViewModel by sharedViewModel()
    private val epoxyController = CurrencyInfoEpoxyController()

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentListResultBinding = FragmentListResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerview?.setController(epoxyController)
        registerObserver()
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        super.onDestroyView()
    }

    private fun registerObserver() {
        viewModel.currencyList.observe(viewLifecycleOwner, {
            epoxyController.setCurrencyList(it)
        })
        epoxyController.clickEvent.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), "Click event show toast", Snackbar.LENGTH_SHORT).show()
        })
    }
}