package com.resurrection.imkb.ui.main.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
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
import com.resurrection.imkb.ui.base.BaseBottomSheetFragment
import com.resurrection.imkb.util.AESFunction
import com.resurrection.imkb.util.Status.*
import com.resurrection.imkb.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailFragment : BaseBottomSheetFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()
    private var detailResponse: DetailResponse? = null
    val entries: ArrayList<Entry> = ArrayList<Entry>()
    private var favoriteState: Boolean = false
    override fun getLayoutRes(): Int = R.layout.fragment_detail
    private lateinit var handshakeResponse: HandshakeResponse
    override fun init(savedInstanceState: Bundle?) {
        setViewModelsObserve()
        val id = arguments?.getString("id")
        handshakeResponse = arguments?.get("handShake") as HandshakeResponse

        id?.let {
            handshakeResponse?.let {
                viewModel.getDetail(
                    handshakeResponse.authorization, DetailRequest(
                        AESFunction.encrypt(
                            id.toDouble().toInt().toString(),
                            handshakeResponse.aesKey,
                            handshakeResponse.aesIV
                        )
                    )
                )
                println(id+"  "+handshakeResponse)
                viewModel.getFavoriteState(id.toDouble())
            }
        }

        binding.favoriteImageView.setOnClickListener {
            if (!favoriteState) {
                detailResponse?.let {
                    var stock: Stock = Stock(
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
                    viewModel.insertFavorite(stock)
                }
            } else {
                // remove state
                detailResponse?.let {
                    var stock: Stock = Stock(
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
                    viewModel.deleteFavorite(stock)
                }
            }
        }
    }

    private fun setViewModelsObserve() {
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { response->
                        detailResponse = response


                        var decryptedDetailResponse: DetailResponse = response
                        decryptedDetailResponse.symbol?.let {
                            decryptedDetailResponse.symbol = AESFunction.decrypt(
                                it,
                                handshakeResponse.aesKey,
                                handshakeResponse.aesIV
                            )
                        }

                        binding.detailResponse = decryptedDetailResponse
                        response.graphicData?.let {
                            it.forEach { data ->
                                entries.add(Entry(data.day.toFloat(), data.value.toFloat()))
                            }
                        }?:run {
                            binding.chart.visibility = View.INVISIBLE
                            binding.dataNotFound.visibility = View.VISIBLE
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
                        }

                        binding.chart.axisLeft.textColor = Color.WHITE; // left y-axis
                        binding.chart.xAxis.textColor = Color.WHITE;
                        binding.chart.legend.textColor = Color.WHITE;
                        binding.chart.description.textColor = Color.WHITE;
                        binding.chart.data.setValueTextColor(Color.WHITE)
                    }
                }
                ERROR -> {
                }
                LOADING -> {
                }
            }
        })

        viewModel.isAdded.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    binding.favoriteImageView.changeIconColor(true)
                    toast(requireContext(), "added to favorite")
                    favoriteState = true
                }
                ERROR -> {
                    binding.favoriteImageView.changeIconColor(false)
                    toast(requireContext(), "could not be added to favorite")
                }
                LOADING -> {
                }
            }
        })

        viewModel.isDeleted.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let { data ->
                        binding.favoriteImageView.changeIconColor(false)
                        toast(requireContext(), "removed to favorite")
                        favoriteState = false
                    }
                }
                ERROR -> {
                    binding.favoriteImageView.changeIconColor(true)
                    toast(requireContext(), "could not be removed")
                    favoriteState = true
                }
                LOADING -> {
                }
            }
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            println(it.status)
            when (it.status) {
                SUCCESS -> {
                    it.data?.let {
                        binding.favoriteImageView.changeIconColor(it)
                        favoriteState = it
                        println(">>>>>>"+favoriteState)
                    }
                }
                ERROR -> {
                    binding.favoriteImageView.changeIconColor(false)
                }
                LOADING -> {
                }
            }
        })
    }

    private infix fun ImageView.changeIconColor(isFavourite: Boolean) {
        val color = if (isFavourite) R.color.white else R.color.darker_gray
        this.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(requireContext(), color),
            PorterDuff.Mode.SRC_IN
        )
    }
}
