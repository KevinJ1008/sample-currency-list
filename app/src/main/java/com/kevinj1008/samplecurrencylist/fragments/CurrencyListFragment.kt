package com.kevinj1008.samplecurrencylist.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kevinj1008.basecore.base.BaseFragment
import com.kevinj1008.samplecurrencylist.databinding.FragmentListResultBinding

class CurrencyListFragment : BaseFragment<FragmentListResultBinding>() {

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentListResultBinding = FragmentListResultBinding::inflate
}