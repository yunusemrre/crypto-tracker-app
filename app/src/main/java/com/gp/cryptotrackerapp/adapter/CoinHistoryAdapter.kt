package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.databinding.ItemCoinHistoryBinding

class CoinHistoryAdapter(
    private var coinHistoryList : ArrayList<ArrayList<Int>>
) : RecyclerView.Adapter<CoinHistoryAdapter.HistoryHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            ItemCoinHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) = holder.bind(
        coinHistoryList[position]
    )

    override fun getItemCount(): Int = coinHistoryList.size


    class HistoryHolder(private var binding: ItemCoinHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArrayList<Int>) {
            binding.mtvItemCoinHisDate.text = item[0].toString()
            binding.mtvItemCoinHisValue.text = item[1].toString()
        }
    }
}