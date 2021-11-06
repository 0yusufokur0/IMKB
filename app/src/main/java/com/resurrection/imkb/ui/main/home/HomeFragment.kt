package com.resurrection.imkb.ui.main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.resurrection.imkb.BR
import com.resurrection.imkb.R
import com.resurrection.imkb.data.model.handshake.HandshakeRequest
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.ListRequest
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.databinding.FragmentHomeBinding
import com.resurrection.imkb.databinding.StockItemBinding
import com.resurrection.imkb.ui.base.BaseFragment
import com.resurrection.imkb.ui.main.MainActivity
import com.resurrection.imkb.ui.main.adapters.SORT.*
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.ui.main.detail.DetailFragment
import com.resurrection.imkb.util.AESFunction
import com.resurrection.imkb.util.DataStoreHelper

import com.resurrection.imkb.util.Status.*

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private var stockPeriod = "all"
    private var detailFragment: DetailFragment? = null
    private var handshakeResponse: HandshakeResponse? = null
    private var stockAdapter: StockAdapter<Stock, StockItemBinding>? = null
    override fun getLayoutRes(): Int = R.layout.fragment_home
    private var tempList = arrayListOf<Stock>()
    override fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        fetchList()
        setViewModelObserve()
        binding.symbol.sortClick { stockAdapter?.sortByItem(SYMBOL) }
        binding.price.sortClick { stockAdapter?.sortByItem(PRICE) }
        binding.difference.sortClick { stockAdapter?.sortByItem(DIFFERENCE) }
        binding.volume.sortClick { stockAdapter?.sortByItem(VOLUME) }
        binding.bid.sortClick { stockAdapter?.sortByItem(BID) }
        binding.offer.sortClick { stockAdapter?.sortByItem(OFFER) }
        binding.change.sortClick { stockAdapter?.sortByItem(CHANGE) }

        (requireActivity() as MainActivity).setTextChangedFun {
            it.let { // TODO:  text i silerken  değişmiyor
                stockAdapter?.updateList(tempList)
                stockAdapter?.filter?.filter(it)
            }
        }
    }

    private fun setViewModelObserve() {
        viewModel.auth.observe(this, Observer { handshake ->
            when (handshake.status) {
                SUCCESS -> {
                    handshake.data?.let { handShakeData ->
                        handshakeResponse = handShakeData
                        viewModel.getResponseList(
                            handShakeData.authorization,
                            ListRequest(
                                AESFunction.encrypt(
                                    stockPeriod,
                                    handShakeData.aesKey,
                                    handShakeData.aesIV
                                )
                            )
                        )
                    }

                }
                LOADING -> {
                }
                ERROR -> {
                }
            }
        })


        viewModel.listResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { listResponse ->

                        var list = arrayListOf(listResponse.stocks)

                        handshakeResponse?.let {
                            binding.listRecyclerView.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            tempList = listResponse.stocks as ArrayList<Stock>
                            stockAdapter =
                                StockAdapter<Stock, StockItemBinding>(
                                    requireContext(),
                                    R.layout.stock_item,
                                    listResponse.stocks,
                                    BR.stock,
                                    handshakeResponse!!.aesKey,
                                    handshakeResponse!!.aesIV,
                                ) { stock ->
                                    onAdapterClick(handshakeResponse!!, stock.id.toString())
                                }
                            binding.listRecyclerView.itemAnimator = null;


                            binding.listRecyclerView.adapter = stockAdapter
                            binding.progressBar.visibility = View.INVISIBLE
                            stockAdapter?.sortByItem(SYMBOL)
                            binding.symbol.setBackgroundColor(Color.parseColor("#2A7EC7"))

                            lifecycleScope.launch {
                                DataStoreHelper(requireContext()).dsSave(
                                    "handshakeResponse",
                                    handshakeResponse!!
                                )
                            }


                        }
                    }
                }
                LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_all -> stockPeriod = "all"
            R.id.action_increasing -> stockPeriod = "increasing"
            R.id.action_decreasing -> stockPeriod = "decreasing"
            R.id.action_volume30 -> stockPeriod = "volume30"
            R.id.action_volume50 -> stockPeriod = "volume50"
            R.id.action_volume100 -> stockPeriod = "volume100"
        }
        fetchList()
        return true
    }

    private fun onAdapterClick(handshakeResponse: HandshakeResponse, id: String) {
        detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putSerializable("handShake", handshakeResponse)
        bundle.putString("id", id)
        detailFragment!!.arguments = bundle
        detailFragment!!.show(parentFragmentManager, "Bottom Sheet")
    }

    @SuppressLint("HardwareIds")
    private fun fetchList() {
        val id: String =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        val model = Build.MODEL
        val manufacturer = Build.MANUFACTURER
        val platformName = Build.MODEL
        val systemVersion = Build.VERSION.INCREMENTAL

        viewModel.getAuth(HandshakeRequest(id, model, manufacturer, platformName, systemVersion))

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
}


