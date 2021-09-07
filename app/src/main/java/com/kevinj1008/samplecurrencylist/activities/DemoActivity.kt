package com.kevinj1008.samplecurrencylist.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.kevinj1008.basecore.base.BaseViewBindingActivity
import com.kevinj1008.localclient.helper.SortOrder
import com.kevinj1008.samplecurrencylist.R
import com.kevinj1008.samplecurrencylist.databinding.ActivityMainBinding
import com.kevinj1008.samplecurrencylist.fragments.CurrencyListFragment
import com.kevinj1008.samplecurrencylist.interfaces.CustomFragmentManager
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity : BaseViewBindingActivity<ActivityMainBinding>(), CustomFragmentManager {

    //Let viewModel belongs to DemoActivity, and it will provide currencyList to CurrencyListFragment
    private val viewModel: CurrencyViewModel by viewModel()
    private var sortOrder: SortOrder = SortOrder.ORIGIN

    override val bindingInflater: (inflater: LayoutInflater
    ) -> ActivityMainBinding = ActivityMainBinding::inflate

    /**
     * This method is better than fragmentOnAttachListener, because listener won't be called if
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
        sortOrder = savedInstanceState?.getSerializable(SORT_ORDER) as? SortOrder ?: SortOrder.ORIGIN
        if (supportFragmentManager.backStackEntryCount > 0) {
            viewModel.sortByOrder(sortOrder)
        }
        initViews()
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
            when (sortOrder) {
                SortOrder.ORIGIN -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_asc)
                }
                SortOrder.ASCENDING -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_des)
                }
                SortOrder.DESCENDING -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_origin)
                }
            }
        } else {
            sortOrder = SortOrder.ORIGIN
        }
        binding?.btnSort?.isVisible = isVisible
    }

    override fun onDataLoading(isLoading: Boolean) {
        binding?.btnSort?.isClickable = !isLoading
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
            //We don't have to do specific behavior to prevent concurrency issue of multi click in here,
            // because Room and LiveData will help use to make every result value synchronized
            sortOrder = when (sortOrder) {
                SortOrder.ORIGIN -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_des)
                    SortOrder.ASCENDING
                }
                SortOrder.ASCENDING -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_origin)
                    SortOrder.DESCENDING
                }
                SortOrder.DESCENDING -> {
                    binding?.btnSort?.text = getString(R.string.sorting_btn_asc)
                    SortOrder.ORIGIN
                }
            }
            viewModel.sortByOrder(sortOrder)
        }
        binding?.logo?.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        private const val FRAGMENT_TAG = "currency_list_result"
        private const val SORT_ORDER = "sort_order"
    }
}