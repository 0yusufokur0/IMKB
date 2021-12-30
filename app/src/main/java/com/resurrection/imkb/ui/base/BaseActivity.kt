package com.resurrection.imkb.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.resurrection.imkb.ui.base_test.AppActivity


abstract class BaseActivity<viewDataBinding : ViewDataBinding>(@LayoutRes val reIsd: Int) : AppActivity<viewDataBinding>(reIsd){


}