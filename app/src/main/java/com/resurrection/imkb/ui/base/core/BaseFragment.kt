package com.resurrection.imkb.ui.base.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.resurrection.imkb.ui.base.AppSession
import javax.inject.Inject


abstract class BaseFragment<VDB : ViewDataBinding,VM:ViewModel>
    (@LayoutRes val resLayoutId:Int,     private val viewModelClass: Class<VM>
) : Fragment() {

    @Inject
    lateinit var appSession: AppSession

    private var _binding: VDB? = null
    val binding get() = _binding!!

    protected val viewModel by lazy { ViewModelProvider(this).get(viewModelClass) }

    abstract fun init(savedInstanceState: Bundle?)

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, resLayoutId, container, false)
        return _binding!!.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}