package com.stocky.adapters

import com.stocky.model.Product
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.stocky.R
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.widget.Toast
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.ArrayList
import java.util.HashMap

class ProductsAdapter(private val products: ArrayList<Product?>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]?.descricao
        val qtd = products[position]?.qtdEstoque
        val dateChange = products[position]?.dt_alteracao
        holder.productName.text = product
        holder.stockQuantity.text = holder.stockQuantity.context.getString(R.string.product_quantity, qtd.toString())
        holder.dateChange.text = holder.dateChange.context.getString(R.string.product_date_change, dateChange)
        holder.productView.setOnClickListener { v ->
            val productToUpdateName = products[position]
            val textNewProductName = EditText(v.context)
            textNewProductName.hint = v.context.getString(R.string.type_new_product_name)
            textNewProductName.setPadding(16, 16, 16, 16)
            MaterialAlertDialogBuilder(v.context)
                .setTitle(v.context.getString(R.string.change_product))
                .setIcon(R.drawable.ic_warning)
                .setMessage(v.context.getString(R.string.change_product_description, productToUpdateName?.descricao))
                .setCancelable(false)
                .setView(textNewProductName)

                .setPositiveButton(v.context.getString(R.string.save)) { _, _ ->
                    val id = productToUpdateName?.id
                    val description = productToUpdateName?.descricao
                    val newProductText = textNewProductName.text.toString()
                    val hashMapProductDesc: HashMap<String, Any> = HashMap<String, Any>()
                    val hashMapHistoryProd: HashMap<String, Any> = HashMap<String, Any>()
                    hashMapProductDesc["descricao"] = newProductText
                    val databaseProducts =
                        FirebaseDatabase.getInstance().getReference("products").child(
                            id!!
                        )
                    val databaseHistory = FirebaseDatabase.getInstance().getReference("history")
                    val historyQuery = databaseHistory.orderByChild("product").equalTo(description)
                    databaseProducts.updateChildren(hashMapProductDesc).addOnSuccessListener {
                        historyQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (historySnapshot in snapshot.children) {
                                    hashMapHistoryProd[historySnapshot.key + "/product"] =
                                        newProductText
                                }
                                databaseHistory.updateChildren(hashMapHistoryProd)
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })
                    }
                }

                .setNegativeButton(v.context.getString(R.string.cancel), null).show()
        }
        holder.btnRemoveProduct.setOnClickListener { v ->
            val productToDelete = products[position]
            MaterialAlertDialogBuilder(v.context)
                .setTitle(v.context.getString(R.string.delete_product))
                .setIcon(R.drawable.ic_warning)
                .setMessage(v.context.getString(R.string.confirm_delete_product, productToDelete?.descricao))
                .setCancelable(false)
                .setPositiveButton(v.context.getString(R.string.yes)) { _, _ ->
                    val id = productToDelete?.id
                    val description = productToDelete?.descricao
                    val databaseProducts =
                        FirebaseDatabase.getInstance().getReference("products").child(
                            id!!
                        )
                    val databaseHistory = FirebaseDatabase.getInstance().getReference("history")
                    val historyQuery = databaseHistory.orderByChild("product").equalTo(description)
                    databaseProducts.removeValue()
                    historyQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (historySnapshot in snapshot.children) {
                                historySnapshot.ref.removeValue()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                    Toast.makeText(
                        v.context,
                        v.context.getString(R.string.product_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }.setNegativeButton(v.context.getString(R.string.no), null).show()
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(var layout: View) : RecyclerView.ViewHolder(
        layout
    ) {
        var productName: TextView = itemView.findViewById(R.id.productName)
        var btnRemoveProduct: com.google.android.material.imageview.ShapeableImageView = itemView.findViewById(R.id.btnRemoveProduct)
        var stockQuantity : TextView = itemView.findViewById(R.id.stockQuantity)
        var dateChange : TextView = itemView.findViewById(R.id.dateChange)
        var productView: ConstraintLayout = itemView.findViewById(R.id.product_view)
    }
}