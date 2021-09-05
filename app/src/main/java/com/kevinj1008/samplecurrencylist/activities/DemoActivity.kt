package com.kevinj1008.samplecurrencylist.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.kevinj1008.basecore.base.BaseViewBindingActivity
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.samplecurrencylist.R
import com.kevinj1008.samplecurrencylist.databinding.ActivityMainBinding
import com.kevinj1008.samplecurrencylist.fragments.CurrencyListFragment
import com.kevinj1008.samplecurrencylist.interfaces.CustomFragmentManager
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity : BaseViewBindingActivity<ActivityMainBinding>(), CustomFragmentManager {

    private val viewModel: CurrencyViewModel by viewModel()
    private var sortOrder: SortOrder = SortOrder.ORIGIN

    override val bindingInflater: (inflater: LayoutInflater
    ) -> ActivityMainBinding = ActivityMainBinding::inflate

    /**
     * This interface is better than fragmentOnAttachListener, because listener won't be called if
     * fragment restored, we want lifeCycle is paired
     */
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is CurrencyListFragment -> {
                fragment.setFragmentManager(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sortOrder = savedInstanceState.getSerializable(SORT_ORDER) as? SortOrder ?: SortOrder.ORIGIN
        if (supportFragmentManager.backStackEntryCount > 0) {
            viewModel.sortByOrder(sortOrder)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SORT_ORDER, sortOrder)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onShowSortingButton(isVisible: Boolean) {
        if (isVisible) {
            //TODO: set correct text
            when (sortOrder) {
                SortOrder.ORIGIN -> {

                }
                SortOrder.ASCENDING -> {

                }
                SortOrder.DESCENDING -> {

                }
            }
        } else {
            //TODO: reset text
            sortOrder = SortOrder.ORIGIN
        }
        binding?.btnSort?.isVisible = isVisible
    }

    private fun initViews() {
        binding?.btnLoad?.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) ?: CurrencyListFragment()
            if (fragment.isAdded) return@setOnClickListener
            viewModel.load()
            supportFragmentManager.commit {
                addToBackStack(FRAGMENT_TAG)
                replace(R.id.container_fragment, fragment, FRAGMENT_TAG)
            }
        }
        binding?.btnSort?.setOnClickListener {
            //TODO: change correct text
            sortOrder = when (sortOrder) {
                SortOrder.ORIGIN -> {
                    SortOrder.ASCENDING
                }
                SortOrder.ASCENDING -> {
                    SortOrder.DESCENDING
                }
                SortOrder.DESCENDING -> {
                    SortOrder.ORIGIN
                }
            }
            viewModel.sortByOrder(sortOrder)
        }
    }

    companion object {
        private const val FRAGMENT_TAG = "currency_list_result"
        private const val SORT_ORDER = "sort_order"
    }
}