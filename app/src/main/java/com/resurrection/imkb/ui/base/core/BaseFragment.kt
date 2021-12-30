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
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.resurrection.imkb.ui.base.data.DataHolderManager
import com.resurrection.imkb.ui.base.data.DataStoreManager
import com.resurrection.imkb.ui.base.data.SharedPreferencesManager
import javax.inject.Inject


abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes val resLayoutId:Int) : Fragment(),
    LifecycleEventObserver {

    @Inject
    lateinit var dataHolderManager: DataHolderManager
    @Inject
    lateinit var dataStoreManager: DataStoreManager
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

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
        dataStoreManager.lifecycleOwner = this
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