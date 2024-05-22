package com.stocky.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.stocky.R
import android.widget.TextView
import android.widget.Toast
import com.stocky.model.Order
import java.util.ArrayList

class OrdersAdapter(private val orders: ArrayList<Order>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_order, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = orders[position].produto
        val quantity = orders[position].quantidade.toString()
        holder.orderName.text = product
        holder.orderQuantity.text = holder.orderQuantity.context.getString(R.string.product_quantity, quantity)
        holder.deleteOrderImage.setOnClickListener {
            orders.removeAt(position)
            this.notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        var orderName: TextView = itemView.findViewById(R.id.orderProductName)
        var orderQuantity: TextView = itemView.findViewById(R.id.orderProductQuantity)
        var deleteOrderImage :com.google.android.material.imageview.ShapeableImageView = itemView.findViewById(R.id.deleteOrder)
    }
}