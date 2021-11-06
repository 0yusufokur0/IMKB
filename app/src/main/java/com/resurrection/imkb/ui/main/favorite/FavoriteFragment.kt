package com.resurrection.imkb.ui.main.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import com.resurrection.imkb.ui.main.MainActivity
import com.resurrection.imkb.ui.main.adapters.SORT
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.ui.main.detail.DetailFragment
import com.resurrection.imkb.util.DataStoreHelper
import com.resurrection.imkb.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch





@AndroidEntryPoint

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private var handshakeResponse:HandshakeResponse? = null
    private val viewModel: FavoriteViewModel by viewModels()
    private var detailFragment: DetailFragment? = null
    private var stockAdapter: StockAdapter<Stock, StockItemBinding>? = null
    private var tempList = arrayListOf<Stock>()

    override fun getLayoutRes(): Int = R.layout.fragment_favorite

    override fun init(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            handshakeResponse = DataStoreHelper(requireContext()).dsGet<HandshakeResponse>("handshakeResponse",HandshakeResponse::class.java)
        }
        setHasOptionsMenu(true)
        setViewModelObserve()
        viewModel.getStocks()

        binding.symbol.sortClick { stockAdapter?.sortByItem(SORT.SYMBOL) }
        binding.price.sortClick { stockAdapter?.sortByItem(SORT.PRICE) }
        binding.difference.sortClick { stockAdapter?.sortByItem(SORT.DIFFERENCE) }
        binding.volume.sortClick { stockAdapter?.sortByItem(SORT.VOLUME) }
        binding.bid.sortClick { stockAdapter?.sortByItem(SORT.BID) }
        binding.offer.sortClick { stockAdapter?.sortByItem(SORT.OFFER) }
        binding.change.sortClick { stockAdapter?.sortByItem(SORT.CHANGE) }

        (requireActivity() as MainActivity).setTextChangedFun {
            it.let { // TODO:  text i silerken  değişmiyor
                stockAdapter?.updateList(tempList)
                stockAdapter?.filter?.filter(it)
            }
        }

    }
    private fun setViewModelObserve(){
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

                            stockAdapter =   StockAdapter<Stock, StockItemBinding>(
                                requireContext(),
                                R.layout.stock_item,
                                list as ArrayList<Stock>,
                                BR.stock,
                                handshakeResponse!!.aesKey,
                                handshakeResponse!!.aesIV,
                            ) { stock ->

                                onAdapterClick(handshakeResponse!!, stock.bid.toString())
                            }
                            binding.listRecyclerView.adapter = stockAdapter
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

    private fun onAdapterClick(handshakeResponse: HandshakeResponse, id: String) {
        detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("handShake", handshakeResponse)
        bundle.putString("id", id)
        detailFragment!!.arguments = bundle
        detailFragment!!.show(parentFragmentManager, "Bottom Sheet")
    }

    private fun TextView.sortClick(func: () -> Unit) {
        this.setOnClickListener {
            func()
            var currentColor: Int = ContextCompat.getColor(requireContext(), R.color.light_gray)
            binding.symbol.setBackgroundColor(currentColor)
            binding.price.setBackgroundColor(currentColor)
            binding.difference.setBackgroundColor(currentColor)
            binding.volume.setBackgroundColor(currentColor)
            binding.bid.setBackgroundColor(currentColor)
            binding.offer.setBackgroundColor(currentColor)
            binding.change.setBackgroundColor(currentColor)
            this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
