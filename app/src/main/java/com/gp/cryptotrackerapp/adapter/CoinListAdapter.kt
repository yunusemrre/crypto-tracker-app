package com.gp.cryptotrackerapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gp.cryptotrackerapp.data.model.CoinInfo.CoinInfoModel
import com.gp.cryptotrackerapp.databinding.ItemCryptoListBinding

class CoinListAdapter() :
    RecyclerView.Adapter<CoinListAdapter.CryptoInfoHolder>() {

    lateinit var listener: CoinListAdapterListener
    private var coinList = mutableListOf<CoinInfoModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(coinInfo : CoinInfoModel){
        val newList = coinList.filter {
            coinInfo.id != it.id
        } as ArrayList<CoinInfoModel>
        newList.add(coinInfo)
        coinList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoInfoHolder {
        return CryptoInfoHolder(
            ItemCryptoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CryptoInfoHolder, position: Int) =
        holder.bind(coinList[position],listener)

    override fun getItemCount(): Int = coinList.size

    class CryptoInfoHolder(private val binding: ItemCryptoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CoinInfoModel, listener: CoinListAdapterListener) {
            binding.mtvItemCoinId.text = item.id
            binding.mtvItemCoinName.text = item.name
            binding.mtvItemCoinSymbol.text = item.symbol

            binding.llItemContainer.setOnClickListener{
                listener.onSelect(item.id.toString())
            }
        }
    }
}

interface CoinListAdapterListener {
    fun onSelect(id: String)
}