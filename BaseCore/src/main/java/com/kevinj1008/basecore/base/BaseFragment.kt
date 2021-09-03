package com.kevinj1008.basecore.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kevinj1008.basecore.BuildConfig

/**
 * Usage:
 * class TestFragment: BaseViewBindingFragment<{$YourViewBindingClassName}>() {
 *      override val bindingInflater: (LayoutInflater) -> {$YourViewBindingClassName} = {$YourViewBindingClassName}::inflate
 *      //If you want write code clearly, you could write as:
 *      override val bindingInflater: (LayoutInflater) -> {$YourViewBindingClassName} = { inflater, container, attachToRoot
 *          {$YourViewBindingClassName}.inflate(inflater, container, attachToRoot)
 *      }
 *      override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
 *          super.onCreateView(inflater, container, savedInstanceState)
 *          //use binding class to reference view to do what you want after super
 *          binding.{$view_id}.{$view_method_you_want_to_use}
 *      }
 * }
 */
abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB?
        get() {
            if (BuildConfig.DEBUG) {
                //You should not call your view in background thread or in some anonymous classes if
                // not release when your lifecycle owner is destroy
                return _binding as VB
            }
            return _binding as? VB
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return performOnCreateView(inflater, container, savedInstanceState)
    }

    protected fun performOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}