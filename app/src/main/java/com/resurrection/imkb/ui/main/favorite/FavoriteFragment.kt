package com.resurrection.imkb.ui.main.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.resurrection.imkb.BR
import com.resurrection.imkb.R
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.databinding.FragmentFavoriteBinding
import com.resurrection.imkb.databinding.StockItemBinding
import com.resurrection.imkb.ui.base.BaseFragment
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.util.DataStoreHelper
import com.resurrection.imkb.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@AndroidEntryPoint

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private var handshakeResponse:HandshakeResponse? = null
    private val viewModel: FavoriteViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_favorite

    override fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        lifecycleScope.launch {
            handshakeResponse = DataStoreHelper(requireContext()).dsGet<HandshakeResponse>("handshakeResponse",HandshakeResponse::class.java)

        }
        viewModel.getStocks()

        viewModel.stocks.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        handshakeResponse?.let {
                            binding.listRecyclerView.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.listRecyclerView.adapter =
                                StockAdapter<Stock, StockItemBinding>(
                                    requireContext(),
                                    R.layout.stock_item,
                                    list as ArrayList<Stock>,
                                    BR.stock,
                                    handshakeResponse!!.aesKey,
                                    handshakeResponse!!.aesIV,
                                ) { stock ->

                                 /*   onAdapterClick(handshakeResponse!!, stock.id.toString())
*/
                                }
                        }


                    }

                }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}
