package com.gp.cryptotrackerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.R
import com.gp.cryptotrackerapp.data.model.coininfo.CoinInfoModel
import com.gp.cryptotrackerapp.databinding.ItemCoinListBinding
import com.gp.cryptotrackerapp.util.enum.CurrencyEnum
import com.gp.cryptotrackerapp.util.extension.round2DecimalVol
import com.gp.cryptotrackerapp.util.extension.round3Decimal

class CoinListAdapter :
    RecyclerView.Adapter<CoinListAdapter.CryptoInfoHolder>() {

    lateinit var listener: CoinListAdapterListener
    private var coinList = mutableListOf<CoinInfoModel>()
    var currency: String = CurrencyEnum.USD.name

    /**
     * Add item as [CoinInfoModel] to adapter data
     * If data not exist add to [coinList]
     * If data exist change market data
     */
    fun setData(coinInfo: CoinInfoModel) {

        val contain = coinList.firstOrNull {
            coinInfo.id == it.id
        }

        if (contain == null) {
            coinList.add(coinInfo)
            notifyDataSetChanged()
        } else {
            coinList.forEachIndexed { index, item ->
                if (item.id == coinInfo.id) {
                    item.marketData = coinInfo.marketData
                    notifyItemChanged(index)
                }
            }
        }
        coinList = coinList.sortedBy { it.name }.toMutableList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoInfoHolder {
        return CryptoInfoHolder(
            ItemCoinListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CryptoInfoHolder, position: Int) =
        holder.bind(coinList[position], this.currency, listener)

    override fun getItemCount(): Int = coinList.size

    class CryptoInfoHolder(private val binding: ItemCoinListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinInfoModel, currency: String, listener: CoinListAdapterListener) {
            binding.mtvItemCoinName.text = item.symbol
            (item.marketData?.currentPrice?.turkishLira.toString() + " try ").also {
                binding.mtvItemCoinLastValTry.text = it
            }
            ("% " + item.marketData?.priceChangePerc24?.round3Decimal()).also {
                binding.mtvItemCoinRate24.text = it
            }

            item.marketData?.priceChangePerc24.toString().let {
                if (it.startsWith('-')) {
                    binding.cvItemCoinRate24.setBackgroundResource(R.color.softRed)
                } else {
                    binding.cvItemCoinRate24.setBackgroundResource(R.color.green300)
                }
            }

            when (currency) {
                CurrencyEnum.USD.name -> {
                    (item.marketData?.currentPrice?.usd.toString() + " usd").also {
                        binding.mtvItemCoinLastVal.text = it
                    }
                    ("vol " + item.marketData?.totalVolumeCurrency?.usd?.round2DecimalVol() + " billion").also {
                        binding.mtvItemCoinVolume.text = it
                    }
                    (" / " + CurrencyEnum.USD.name).also { binding.mtvItemCoinCurrency.text = it }
                }
                CurrencyEnum.EUR.name -> {
                    (item.marketData?.currentPrice?.euro.toString() + " eur").also {
                        binding.mtvItemCoinLastVal.text = it
                    }
                    ("vol " + item.marketData?.totalVolumeCurrency?.euro?.round2DecimalVol() + " billion").also {
                        binding.mtvItemCoinVolume.text = it
                    }
                    (" / " + CurrencyEnum.EUR.name).also { binding.mtvItemCoinCurrency.text = it }
                }
                CurrencyEnum.TRY.name -> {
                    (item.marketData?.currentPrice?.turkishLira.toString() + " try").also {
                        binding.mtvItemCoinLastVal.text = it
                    }
                    ("vol " + item.marketData?.totalVolumeCurrency?.turkishLira?.round2DecimalVol() + " billion").also {
                        binding.mtvItemCoinVolume.text = it
                    }
                    (" / " + CurrencyEnum.TRY.name).also { binding.mtvItemCoinCurrency.text = it }
                }
            }

            binding.cvItemCoinAlert.setOnClickListener {
                listener.onAlert(item.id.toString())
            }

            binding.llItemCoinContainer.setOnClickListener {
                listener.onSelect(item.id.toString(), item.name.toString())
            }
        }
    }
}

/**
 * Listener for selecting coin and setting alarm
 */
interface CoinListAdapterListener {
    fun onSelect(id: String, name: String)
    fun onAlert(id: String)
}