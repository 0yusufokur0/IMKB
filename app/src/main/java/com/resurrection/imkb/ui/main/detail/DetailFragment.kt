package com.resurrection.imkb.ui.main.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.resurrection.imkb.R
import com.resurrection.imkb.data.model.handshake.HandshakeResponse
import com.resurrection.imkb.data.model.imkb.DetailRequest
import com.resurrection.imkb.data.model.imkb.DetailResponse
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.databinding.FragmentDetailBinding
import com.resurrection.imkb.ui.app.BaseBottomSheetFragment
import com.resurrection.imkb.util.AESFunction
import com.resurrection.imkb.ui.base.data.Status.*
import com.resurrection.imkb.ui.base.general.ThrowableError
import com.resurrection.imkb.ui.base.general.tryCatch

import com.resurrection.imkb.ui.base.general.toast

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.view.*

@AndroidEntryPoint
class DetailFragment : BaseBottomSheetFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()
    private var encryptedDetailResponse: DetailResponse? = null
    private val entries: ArrayList<Entry> = ArrayList()
    private var favoriteState: Boolean = false
    private lateinit var handshakeResponse: HandshakeResponse

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun init(savedInstanceState: Bundle?) {
        setViewModelsObserve()
        val id = arguments?.getString("id")
        handshakeResponse = arguments?.get("handShake") as HandshakeResponse

        id?.let {
            handshakeResponse.let {
                viewModel.getDetail(
                    handshakeResponse.authorization, DetailRequest(
                        AESFunction.encrypt(
                            id.toDouble().toInt().toString(),
                            handshakeResponse.aesKey,
                            handshakeResponse.aesIV
                        )
                    )
                )
                viewModel.getFavoriteState(id.toDouble())
            }
        }

        binding.favoriteImageView.setOnClickListener {
            encryptedDetailResponse?.let {
                val stock = Stock(
                    it.bid.toInt(),
                    id!!.toDouble(),
                    it.difference,
                    it.isDown,
                    it.isUp,
                    it.offer,
                    it.price,
                    it.symbol,
                    it.volume
                )

                if (!favoriteState) viewModel.insertFavorite(stock)
                else viewModel.deleteFavorite(stock)
            }
        }
    }

    private fun setViewModelsObserve() {
        viewModel.detail.observe(viewLifecycleOwner, Observer{
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { response ->

                        encryptedDetailResponse = response
                        val decryptedDetailResponse: DetailResponse = response

                        response.symbol.let { symbol ->
                            tryCatch {
                                decryptedDetailResponse.symbol =
                                    AESFunction.decrypt(symbol, handshakeResponse.aesKey,
                                        handshakeResponse.aesIV)

                                                    binding.detailResponse = decryptedDetailResponse

                        response.graphicData.let { list ->
                            list.forEach { data ->
                                entries.add(Entry(data.day.toFloat(), data.value.toFloat()))
                            }
                        }

                        val vl = LineDataSet(entries, "Price")
                        vl.setDrawValues(true)
                        vl.setDrawFilled(true)
                        vl.lineWidth = 3f

                        binding.chart.apply {
                            xAxis.labelRotationAngle = 0f
                            chart.data = LineData(vl)
                            axisRight.isEnabled = false
                            setTouchEnabled(true)
                            setPinchZoom(true)
                            description.text = ""
                            setNoDataText("graph not found")
                            animateX(1800, Easing.EaseInExpo)
                            chart.axisLeft.textColor = Color.WHITE // left y-axis
                            chart.xAxis.textColor = Color.WHITE
                            chart.legend.textColor = Color.WHITE
                            chart.description.textColor = Color.WHITE
                            chart.data.setValueTextColor(Color.WHITE)

                        }

                            }
                        }
                    }
                }
                ERROR -> {
                    ThrowableError(it.message.toString())
                    toast("Failed to load data")
                }
                LOADING -> { }
            }
        })

        viewModel.isAdded.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    binding.favoriteImageView.changeIconColor(true)
                    toast("added to favorite")
                }
                ERROR -> {
                    binding.favoriteImageView.changeIconColor(false)
                    toast( "could not be added to favorite")
                }
                LOADING -> { }
            }
        })

        viewModel.isDeleted.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    binding.favoriteImageView.changeIconColor(false)
                    toast( "removed to favorite")

                }
                ERROR -> {
                    binding.favoriteImageView.changeIconColor(true)
                    toast("could not be removed")

                }
                LOADING -> { }
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> binding.favoriteImageView.changeIconColor(it.data)
                ERROR -> binding.favoriteImageView.changeIconColor(false)
                LOADING -> { }
            }
        })
    }

    private infix fun ImageView.changeIconColor(isFavourite: Boolean?) {
        isFavourite?.let {
            val color = if (isFavourite) R.color.white else R.color.darker_gray
            this.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(requireContext(), color),
                PorterDuff.Mode.SRC_IN
            )
            favoriteState = isFavourite
        }
    }
}
