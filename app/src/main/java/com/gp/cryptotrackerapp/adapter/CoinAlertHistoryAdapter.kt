package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.databinding.ItemCoinAlertHistoryBinding
import com.gp.cryptotrackerapp.util.extension.formatDate
import com.gp.cryptotrackerapp.util.extension.formatDateSimple

class CoinAlertHistoryAdapter(
    private var coinHistoryList: ArrayList<CoinInfoModel>,
    private var listener: CoinAlertHistoryAdapterListener
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
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) =
        holder.bind(coinHistoryList[position])

    override fun getItemCount(): Int = coinHistoryList.size

    class HistoryHolder(
        private var binding: ItemCoinAlertHistoryBinding,
        private var listener: CoinAlertHistoryAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinInfoModel) {
            binding.mtvAlertHistoryCoin.text = item.id.toString().replaceFirstChar { c -> c.uppercaseChar() }
            binding.mtvAlertHistoryMax.text = item.maxAlert.toString()
            binding.mtvAlertHistoryMin.text = item.minAlert.toString()
            binding.mtvAlertHistoryDate.text = item.insertDate?.formatDateSimple()
            binding.cbAlertHistoryState.isChecked = item.active == true

            if (item.active == true) {
                binding.llItemCoinAlert.setBackgroundResource(R.color.green300)
            } else {
                binding.llItemCoinAlert.setBackgroundResource(R.color.softRed)
            }

            binding.cbAlertHistoryState.setOnCheckedChangeListener { _, state ->
                item.alertEntityId?.let { id ->
                    item.active = state
                    listener.setAlertState(id, state)
                    if(state){
                        binding.llItemCoinAlert.setBackgroundResource(R.color.green300)
                    }else{
                        binding.llItemCoinAlert.setBackgroundResource(R.color.softRed)
                    }

                }
            }
        }
    }
}

interface CoinAlertHistoryAdapterListener {
    fun setAlertState(entityId: Int, state: Boolean)
}