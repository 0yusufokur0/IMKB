package com.resurrection.imkb.ui.main.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import com.resurrection.imkb.R
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.databinding.StockItemBinding
import com.resurrection.imkb.ui.base.BaseAdapter
import com.resurrection.imkb.ui.main.adapters.SORT.*
import com.resurrection.imkb.util.AESFunction
import java.util.*
import kotlin.collections.ArrayList

enum class SORT {
    SYMBOL,
    PRICE,
    DIFFERENCE,
    VOLUME,
    BID,
    OFFER,
    CHANGE
}

class StockAdapter<T, viewDataBinding : ViewDataBinding>(
    mLayoutResource: Int,
    mList: ArrayList<T>,
    mItemId: Int,
    aesKey: String, aesIV: String,
    mOnItemClick: (T) -> Unit
) : BaseAdapter<T, viewDataBinding>(mLayoutResource, mList, mItemId, mOnItemClick), Filterable {
    var aesKey = aesKey
    var aesIV = aesIV
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        super.onBindViewHolder(holder, position)

        try {
            (currentList as ArrayList<Stock>)[position].symbol =
                AESFunction.decrypt((currentList[position] as Stock).symbol, aesKey, aesIV)
        } catch (e: Exception) { }

        var stateString = ""
        var stateTextColor = 0

        if ((currentList as ArrayList<Stock>)[position].isDown) {
            stateString = "▼"
            stateTextColor = Color.RED
        } else if ((currentList as ArrayList<Stock>)[position].isUp) {
            stateString = "▲"
            stateTextColor = Color.GREEN
        } else {
            stateString = "━"
            (binding as StockItemBinding).price.setTextColor(Color.GRAY)
            stateTextColor = Color.GRAY
        }


        if ((currentList[position] as Stock).difference == 0.0){
            stateTextColor = Color.GRAY
            stateString = "━"
        }
        (binding as StockItemBinding).change.text = stateString
        (binding as StockItemBinding).change.setTextColor(Color.BLACK)
        (binding as StockItemBinding).stockCard.setCardBackgroundColor(Color.WHITE)
        (binding as StockItemBinding).difference.setTextColor(stateTextColor)
        (binding as StockItemBinding).volume.setTextColor(stateTextColor)
        (binding as StockItemBinding).bid.setTextColor(stateTextColor)
        (binding as StockItemBinding).offer.setTextColor(stateTextColor)
        (binding as StockItemBinding).price.setTextColor(stateTextColor)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortByItem(sortType: SORT) {
        if (currentList.size != 0) {

            val mutable: MutableList<Stock> = currentList.toMutableList() as MutableList<Stock>
            if (currentList.size != 1) {
                when (sortType) {
                    SYMBOL -> mutable.sortBy { it.symbol }
                    PRICE -> mutable.sortBy { it.price }
                    DIFFERENCE -> mutable.sortBy { it.difference }
                    VOLUME -> mutable.sortBy { it.volume }
                    BID -> mutable.sortBy { it.bid }
                    OFFER -> mutable.sortBy { it.offer }
                    CHANGE -> mutable.sortBy { it.isUp || it.isDown }
                }

                if (mutable[0] == currentList[0]) mutable.reverse()

                currentList = mutable.toList() as ArrayList<Stock> as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }

    fun setList(list: ArrayList<Stock>) {
        currentList = list as ArrayList<T>
        notifyDataSetChanged()
    }


/*

    private fun sort(sortType: SORT ,mutable: MutableList<Stock>): MutableList<Stock> {

        return mutable
    }
*/


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    // default
                    currentList = currentList
                } else {
                    val resultList = ArrayList<Stock>()
                    for (row in (currentList as ArrayList<Stock>)) {
                        if (row.symbol.contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                        if (row.symbol.contains(charSearch.uppercase(Locale.ROOT))) {
                            resultList.add(row)
                        }

                    }
                    currentList = resultList as ArrayList<T>
                }
                val filterResults = FilterResults()
                filterResults.values = currentList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                currentList = (results?.values as ArrayList<Stock>) as ArrayList<T>
                notifyDataSetChanged()
            }

        }
    }


}
