package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.databinding.ItemCoinAlertHistoryBinding

class CoinAlertHistoryAdapter(
    private var coinHistoryList: ArrayList<CoinInfoModel>
) : RecyclerView.Adapter<CoinAlertHistoryAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryHolder {
        return HistoryHolder(
            ItemCoinAlertHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) =
        holder.bind(coinHistoryList[position])

    override fun getItemCount(): Int = coinHistoryList.size

    class HistoryHolder(private var binding: ItemCoinAlertHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinInfoModel) {
            binding.mtvAlertHistoryCoin.text = item.id
            binding.mtvAlertHistoryMax.text = item.maxAlert.toString()
            binding.mtvAlertHistoryMin.text = item.minAlert.toString()
        }
    }


}