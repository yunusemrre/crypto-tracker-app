package com.gp.cryptotrackerapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel

class CoinListDiffUtils(private val oldList: List<CoinInfoModel>, private val newList: List<CoinInfoModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id === newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, value, symbol) = oldList[oldItemPosition]
        val (_, value1, name1) = newList[newItemPosition]
        return symbol == name1 && value == value1
    }

}