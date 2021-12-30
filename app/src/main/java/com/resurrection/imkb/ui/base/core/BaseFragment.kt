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
import com.resurrection.imkb.ui.base.di.AppSession
import javax.inject.Inject


abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes val resLayoutId:Int) : Fragment() {

    @Inject
    lateinit var appSession: AppSession

    private var _binding: VDB? = null
    val binding get() = _binding!!

    abstract fun init(savedInstanceState: Bundle?)

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, resLayoutId, container, false)
        appSession.dataStore.lifecycleOwner = this
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