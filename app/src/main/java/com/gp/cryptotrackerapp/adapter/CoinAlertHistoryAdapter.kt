package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.databinding.ItemCoinAlertHistoryBinding
import com.gp.cryptotrackerapp.util.extension.formatDateSimple

/**
 * Adapter for user coin alert history
 */
class CoinAlertHistoryAdapter(
    private var listener: CoinAlertHistoryAdapterListener
) : RecyclerView.Adapter<CoinAlertHistoryAdapter.HistoryHolder>() {

    private var coinHistoryList = arrayListOf<CoinInfoModel>()

    /**
     * Set adapter data
     */
    fun setData(data: ArrayList<CoinInfoModel>) {
        this.coinHistoryList = data
        notifyDataSetChanged()
    }

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
            binding.mtvAlertHistoryCurrency.text = item.currency.toString()

            binding.mtvAlertHistoryDate.text = item.insertDate?.formatDateSimple()
            binding.cbAlertHistoryState.isChecked = item.active == true

            item.maxAlert?.let {
                binding.mtvAlertHistoryValue.text = item.maxAlert.toString()
                binding.mtvAlertHistoryTask.text = "Higher"
            }
            item.minAlert?.let {
                binding.mtvAlertHistoryValue.text = item.minAlert.toString()
                binding.mtvAlertHistoryTask.text = "Lower"
            }

            if (item.active == true) {
                binding.llItemCoinAlert.setBackgroundResource(R.color.green300)
            } else {
                binding.llItemCoinAlert.setBackgroundResource(R.color.softRed)
            }

            binding.cbAlertHistoryState.setOnCheckedChangeListener { _, state ->
                item.alertEntityId?.let { id ->
                    item.active = state
                    listener.setAlertState(id, state)
                    if (state) {
                        binding.llItemCoinAlert.setBackgroundResource(R.color.green300)
                    } else {
                        binding.llItemCoinAlert.setBackgroundResource(R.color.softRed)
                    }

                }
            }
        }
    }
}

/**
 * Listener for setting alert state
 */
interface CoinAlertHistoryAdapterListener {
    fun setAlertState(entityId: Int, state: Boolean)
}