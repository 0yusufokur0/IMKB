package com.resurrection.imkb.ui.main.adapters

import android.annotation.SuppressLint
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import com.resurrection.imkb.data.model.imkb.Stock
import com.resurrection.imkb.ui.base.BaseAdapter
import com.resurrection.imkb.ui.main.adapters.SORT.*
import com.resurrection.imkb.util.AESFunction
import java.util.*
import kotlin.Comparator
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
    }


    @SuppressLint("NotifyDataSetChanged")
    fun sortByItem(sortType: SORT) {
        if (currentList.size != 0){


        var mutable: MutableList<Stock> = currentList.toMutableList() as MutableList<Stock>
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

    fun setList( list:ArrayList<Stock>){
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
                    for (row in (currentList as  ArrayList<Stock>)) {
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
