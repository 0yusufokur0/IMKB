package com.resurrection.imkb.ui.main.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.resurrection.imkb.BR
import com.resurrection.imkb.R
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.databinding.FragmentFavoriteBinding
import com.resurrection.imkb.databinding.StockItemBinding
import com.resurrection.imkb.ui.base.core.BaseFragment
import com.resurrection.imkb.ui.main.MainActivity
import com.resurrection.imkb.ui.main.adapters.SORT
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.ui.main.detail.DetailFragment
import com.resurrection.imkb.ui.base.data.Status
import com.resurrection.imkb.ui.base.general.ThrowableError
import com.resurrection.imkb.ui.base.general.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding,FavoriteViewModel>(R.layout.fragment_favorite,FavoriteViewModel::class.java) {
    private var response: HandshakeResponse? = null
    private var detailFragment: DetailFragment? = null
    private var stockAdapter: StockAdapter<Stock, StockItemBinding>? = null
    private var tempList = arrayListOf<Stock>()

    override fun init(savedInstanceState: Bundle?) {
          response = appSession.dataHolder.getSerializable("handshakeResponse") as HandshakeResponse?

        setHasOptionsMenu(true)
        setViewModelObserve()
        viewModel.getStocks()

        binding.apply {
            symbol.sortClick { stockAdapter?.sortByItem(SORT.SYMBOL) }
            price.sortClick { stockAdapter?.sortByItem(SORT.PRICE) }
            difference.sortClick { stockAdapter?.sortByItem(SORT.DIFFERENCE) }
            volume.sortClick { stockAdapter?.sortByItem(SORT.VOLUME) }
            bid.sortClick { stockAdapter?.sortByItem(SORT.BID) }
            offer.sortClick { stockAdapter?.sortByItem(SORT.OFFER) }
            change.sortClick { stockAdapter?.sortByItem(SORT.CHANGE) }
        }


        (requireActivity() as MainActivity).setTextChangedFun {
            it.let {
                stockAdapter?.updateList(tempList)
                stockAdapter?.filter?.filter(it)
            }
        }
    }

    private fun setViewModelObserve() {
        viewModel.stocks.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        response?.let {

                            stockAdapter = StockAdapter(R.layout.stock_item, list as ArrayList<Stock>,
                                BR.stock, response!!.aesKey, response!!.aesIV,requireContext())
                            { stock -> onAdapterClick(response!!, stock.bid.toString()) }

                            binding.listRecyclerView.apply {
                                layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter = stockAdapter
                            }
                        }
                    }
                }
                Status.ERROR -> ThrowableError(it.message.toString())
                Status.LOADING -> toast("loading...")
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
            val currentColor: Int = ContextCompat.getColor(requireContext(), R.color.light_gray)
            binding.apply {
                symbol.setBackgroundColor(currentColor)
                price.setBackgroundColor(currentColor)
                difference.setBackgroundColor(currentColor)
                volume.setBackgroundColor(currentColor)
                bid.setBackgroundColor(currentColor)
                offer.setBackgroundColor(currentColor)
                change.setBackgroundColor(currentColor)
            }
            this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
