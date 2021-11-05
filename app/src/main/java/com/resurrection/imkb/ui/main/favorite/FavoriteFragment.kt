package com.resurrection.imkb.ui.main.favorite

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.resurrection.imkb.R
import com.resurrection.imkb.databinding.FragmentFavoriteBinding
import com.resurrection.imkb.ui.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    override fun getLayoutRes(): Int  = R.layout.fragment_favorite

    override fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}
