package com.resurrection.imkb.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<T, viewDataBinding : ViewDataBinding>(
    var layoutResource: Int,
     var currentList: ArrayList<T>,
    var itemId: Int,
    var onItemClick: (T) -> Unit
) : RecyclerView.Adapter<BaseAdapter.BaseHolder<T>>() {

    lateinit var binding: viewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResource,
            parent,
            false
        )
        return BaseHolder(binding, itemId,onItemClick )
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size



    fun updateList(newList:ArrayList<T>){
        val diffCallBack = BaseDiffUtil<T>(currentList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        diffResult.dispatchUpdatesTo(this)

    }


    class BaseHolder<T>(private var binding: ViewDataBinding, private var itemId: Int,var onItemClick: (T) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(itemId, item)
            itemView.setOnClickListener { onItemClick((item)) }
        }

    }

    class BaseDiffUtil<T> (
        private val oldNumbers: List<T>,
        private val newNumbers: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldNumbers.size

        override fun getNewListSize(): Int = newNumbers.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNumbers[oldItemPosition] == newNumbers[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNumbers[oldItemPosition] == newNumbers[newItemPosition]
        }
    }
}

