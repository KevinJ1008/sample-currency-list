package com.kevinj1008.samplecurrencylist.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import com.kevinj1008.basecore.base.BaseViewBindingActivity
import com.kevinj1008.samplecurrencylist.R
import com.kevinj1008.samplecurrencylist.databinding.ActivityMainBinding
import com.kevinj1008.samplecurrencylist.fragments.CurrencyListFragment
import com.kevinj1008.samplecurrencylist.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val viewModel: CurrencyViewModel by viewModel()

    override val bindingInflater: (inflater: LayoutInflater
    ) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.btnLoad?.setOnClickListener {
            viewModel.load()
            val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) ?: CurrencyListFragment()
            if (fragment.isAdded) return@setOnClickListener
            supportFragmentManager.beginTransaction().apply {
                this.replace(R.id.container_fragment, fragment, FRAGMENT_TAG)
                this.commitAllowingStateLoss()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val FRAGMENT_TAG = "currency_list_result"
    }
}