package com.resurrection.imkb.ui.main.home


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.resurrection.imkb.ui.main.adapters.SORT.*
import com.resurrection.imkb.ui.main.adapters.StockAdapter
import com.resurrection.imkb.ui.main.detail.DetailFragment
import com.resurrection.imkb.util.AESFunction
import com.resurrection.imkb.util.Status.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private var stockPeriod = "all" /*requireArguments().getString("period", "all")*/
    private var detailFragment: DetailFragment? = null
    private var handshakeResponse: HandshakeResponse? = null
    private lateinit var stockAdapter: StockAdapter<Stock, StockItemBinding>
    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun init(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        fetchList()
        setViewModelObserve()
        binding.symbol.sortClick { stockAdapter.sortByItem(SYMBOL) }
        binding.price.sortClick { stockAdapter.sortByItem(PRICE) }
        binding.difference.sortClick { stockAdapter.sortByItem(DIFFERENCE) }
        binding.volume.sortClick { stockAdapter.sortByItem(VOLUME) }
        binding.bid.sortClick { stockAdapter.sortByItem(BID) }
        binding.offer.sortClick { stockAdapter.sortByItem(OFFER) }
        binding.change.sortClick { stockAdapter.sortByItem(CHANGE) }
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

            println(handshake.status)

        })


        viewModel.listResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { listResponse ->
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        var list = arrayListOf(listResponse.stocks)

                        handshakeResponse?.let {
                            binding.listRecyclerView.layoutManager = layoutManager

                            stockAdapter =
                                StockAdapter<Stock, StockItemBinding>(
                                    R.layout.stock_item,
                                    listResponse.stocks as ArrayList<Stock>,
                                    BR.stock,
                                    handshakeResponse!!.aesKey,
                                    handshakeResponse!!.aesIV,
                                ) { stock ->
                                    onAdapterClick(handshakeResponse!!, stock.id.toString())
                                }
                            binding.listRecyclerView.adapter = stockAdapter
                        }
                    }
                }
                LOADING -> {
                }
                ERROR -> {
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
    private fun TextView.sortClick(func:()->Unit){
        this.setOnClickListener {
            func()
            binding.symbol.setBackgroundColor(Color.BLACK)
            binding.price.setBackgroundColor(Color.BLACK)
            binding.difference.setBackgroundColor(Color.BLACK)
            binding.volume.setBackgroundColor(Color.BLACK)
            binding.bid.setBackgroundColor(Color.BLACK)
            binding.offer.setBackgroundColor(Color.BLACK)
            binding.change.setBackgroundColor(Color.BLACK)
            this.setBackgroundColor(Color.RED)
        }
    }
}


