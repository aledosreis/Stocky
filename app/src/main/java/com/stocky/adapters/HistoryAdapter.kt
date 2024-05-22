package com.stocky.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.stocky.R
import android.widget.TextView
import com.stocky.model.History
import java.util.ArrayList

class HistoryAdapter(private val histories: ArrayList<History?>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_history, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = histories[position]?.product.toString()
        val stockQuantity = histories[position]?.qtd.toString()
        val dateStockChange = histories[position]?.date
        val transactionType = histories[position]?.tipoTransacao
        holder.productName.text = product
        holder.productQuantity.text = holder.productQuantity.context.getString(R.string.product_quantity, stockQuantity)
        holder.dateChange.text = holder.dateChange.context.getString(R.string.product_date_change, dateStockChange)
        if (transactionType == KEY_TRANSACTION_STOCK_IN) {
            holder.iconUpDown.setImageResource(R.drawable.ic_arrow_up)

        } else if (transactionType == KEY_TRANSACTION_STOCK_OUT) {
            holder.iconUpDown.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun getItemCount(): Int {
        return histories.size
    }

    class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        var productName: TextView = itemView.findViewById(R.id.productName)
        var productQuantity: TextView = itemView.findViewById(R.id.stockQuantity)
        var dateChange: TextView = itemView.findViewById(R.id.dateChange)
        var iconUpDown : com.google.android.material.imageview.ShapeableImageView = itemView.findViewById(R.id.iconUpDown)

    }

    companion object {
        private const val KEY_TRANSACTION_STOCK_IN = "entrada"
        private const val KEY_TRANSACTION_STOCK_OUT = "saida"
    }
}