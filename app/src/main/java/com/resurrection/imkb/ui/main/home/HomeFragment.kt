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
import com.resurrection.imkb.ui.base.core.BaseFragment
import com.resurrection.imkb.ui.main.MainActivity
import com.resurrection.imkb.ui.main.adapters.SORT.*
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.ui.main.detail.DetailFragment
import com.resurrection.imkb.util.AESFunction
import com.resurrection.imkb.ui.base.data.Status.*
import com.resurrection.imkb.ui.base.general.ThrowableError
import com.resurrection.imkb.ui.base.general.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var stockPeriod = "all"
    private var detailFragment: DetailFragment? = null
    private var response: HandshakeResponse? = null
    private var stockAdapter: StockAdapter<Stock, StockItemBinding>? = null
    private var tempList = arrayListOf<Stock>()

    override fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        fetchList()

        requireContext()

        setViewModelObserve()
        binding.apply {
            symbol.sortClick { stockAdapter?.sortByItem(SYMBOL) }
            price.sortClick { stockAdapter?.sortByItem(PRICE) }
            difference.sortClick { stockAdapter?.sortByItem(DIFFERENCE) }
            volume.sortClick { stockAdapter?.sortByItem(VOLUME) }
            bid.sortClick { stockAdapter?.sortByItem(BID) }
            offer.sortClick { stockAdapter?.sortByItem(OFFER) }
            change.sortClick { stockAdapter?.sortByItem(CHANGE) }
        }


        (requireActivity() as MainActivity).setTextChangedFun {
            it.let {
                stockAdapter?.updateList(tempList)
                stockAdapter?.filter?.filter(it)
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener { fetchList() }
    }

    private fun setViewModelObserve() {
        viewModel.auth.observe(this) { handshake ->
            when (handshake.status) {
                SUCCESS -> {
                    handshake.data?.let { handShakeData ->
                        response = handShakeData
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
                        binding.swipeRefreshLayout.isRefreshing = false
                    }

                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE

                ERROR -> ThrowableError(handshake.message.toString())
            }
        }


        viewModel.listResponse.observe(viewLifecycleOwner, Observer{
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { listResponse ->
                        if (listResponse.status.isSuccess) {

                            response?.let {
                                tempList = listResponse.stocks as ArrayList<Stock>
                                stockAdapter =
                                    StockAdapter(
                                         R.layout.stock_item, listResponse.stocks,
                                        BR.stock, response!!.aesKey, response!!.aesIV,requireContext())
                                    { stock -> onAdapterClick(response!!, stock.id.toString()) }

                                binding.apply {
                                    listRecyclerView.layoutManager =
                                        LinearLayoutManager(
                                            requireContext(),
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )

                                    listRecyclerView.itemAnimator = null
                                    listRecyclerView.adapter = stockAdapter
                                    progressBar.visibility = View.INVISIBLE
                                    symbol.setBackgroundColor(Color.parseColor("#2A7EC7"))
                                }

                                stockAdapter?.sortByItem(SYMBOL)

                                lifecycleScope.launch {
                                    dataStoreManager.insertDataStore("handshakeResponse", response!!)
                                }
                            }
                        }
                    }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE

                ERROR -> binding.progressBar.visibility = View.INVISIBLE

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
        if (true) {
            val id =
                Settings.Secure.getString(
                    requireContext().contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            val model = Build.MODEL
            val manufacturer = Build.MANUFACTURER
            val platformName = Build.MODEL
            val systemVersion = Build.VERSION.INCREMENTAL

            viewModel.getAuth(
                HandshakeRequest(
                    id,
                    model,
                    manufacturer,
                    platformName,
                    systemVersion
                )
            )
        } else toast( "Network is not available")

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
}


