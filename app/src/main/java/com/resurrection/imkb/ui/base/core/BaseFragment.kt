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
import com.resurrection.imkb.ui.base.general.toast
import javax.inject.Inject


abstract class BaseFragment<VDB : ViewDataBinding,VM:ViewModel>
    (@LayoutRes val resLayoutId:Int, private val viewModelClass: Class<VM>
) : Fragment() {

    private var fragmentName = this.javaClass.simpleName

    @Inject
    lateinit var appSession: AppSession

    private var _binding: VDB? = null
    val binding get() = _binding!!

    protected val viewModel by lazy { ViewModelProvider(this).get(viewModelClass) }

    abstract fun init(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appSession.logger.fragmentOnCreate(fragmentName)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appSession.lifecycleOwner = viewLifecycleOwner
        _binding = DataBindingUtil.inflate(inflater, resLayoutId, container, false)
        appSession.logger.fragmentOnCreateView(fragmentName)
        return _binding!!.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        appSession.logger.fragmentOnStart(fragmentName)
    }

    override fun onResume() {
        super.onResume()
        appSession.logger.fragmentOnResume(fragmentName)
    }

    override fun onPause() {
        super.onPause()
        appSession.logger.fragmentOnPause(fragmentName)
    }

    override fun onStop() {
        super.onStop()
        appSession.logger.fragmentOnStop(fragmentName)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        appSession.logger.fragmentOnDestroyView(fragmentName)
        _binding = null

    }

    override fun onDestroy() {
        super.onDestroy()
        appSession.logger.fragmentOnDestroy(fragmentName)
    }
}