package com.example.nhahv.testthpt

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by nhahv on 07/11/2017.
 */
class BaseRecyclerAdapter<T>(private val items: ArrayList<T>, val layout: Int, val listener: OnClickItem<T>) : RecyclerView.Adapter<BaseRecyclerAdapter.BaseVieHolder<T>>() {

    private var inflater: LayoutInflater? = null

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseVieHolder<T>?, position: Int) {
        holder?.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseVieHolder<T> {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent?.context)
        }
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layout, parent, false)
        return BaseVieHolder(binding, listener)
    }

    class BaseVieHolder<in T>(private val binding: ViewDataBinding, val listener: OnClickItem<T>) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, position: Int) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.listener, listener)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }

    interface OnClickItem<in T> {
        fun onClickItem(item: T, position: Int)
    }
}