package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.databinding.ItemCoinHistoryBinding
import com.gp.cryptotrackerapp.util.extension.round3Decimal
import com.gp.cryptotrackerapp.util.extension.toDateString
import kotlin.math.roundToLong

class CoinHistoryAdapter : RecyclerView.Adapter<CoinHistoryAdapter.HistoryHolder>() {

    private lateinit var coinHistoryList: ArrayList<ArrayList<Double>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            ItemCoinHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Set adapter data as [ArrayList]
     */
    fun setList(list: ArrayList<ArrayList<Double>>) {
        list.reverse()
        this.coinHistoryList = list
        notifyDataSetChanged()
    }

    /**
     * Add item to adapter data if the date changed
     */
    fun setData(rate: Double, date: Double) {
        if (coinHistoryList.none { date == it[0] }) {
            this.coinHistoryList.reverse()
            coinHistoryList.add(
                arrayListOf(
                    date,
                    rate
                )
            )
            this.coinHistoryList.reverse()
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) = holder.bind(
        coinHistoryList[position]
    )

    override fun getItemCount(): Int = coinHistoryList.size

    class HistoryHolder(private var binding: ItemCoinHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArrayList<Double>) {
            binding.mtvItemCoinHisDate.text = item[0].roundToLong().toDateString()
            binding.mtvItemCoinHisValue.text = item[1].round3Decimal()
        }
    }
}