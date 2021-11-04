package com.resurrection.imkb.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChanged(){
        notifyDataSetChanged()
    }


    class BaseHolder<T>(private var binding: ViewDataBinding, private var itemId: Int,var onItemClick: (T) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(itemId, item)
            itemView.setOnClickListener { onItemClick((item)) }
        }

    }
}

