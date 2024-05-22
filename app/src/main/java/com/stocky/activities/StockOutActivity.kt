package com.stocky.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stocky.model.Product
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import android.text.TextUtils
import android.widget.*
import com.stocky.R
import com.stocky.databinding.ActivityStockOutBinding
import com.stocky.model.History
import java.text.SimpleDateFormat
import java.util.*

class StockOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStockOutBinding

    lateinit var spinnerProducts: AutoCompleteTextView
    lateinit var produtos: ArrayList<Product?>
    private var selectedPosition : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadProductsInSpinner()
        spinnerProducts.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
        }
        binding.btnStockOut.setOnClickListener { stockOut() }
    }

    private fun loadProductsInSpinner() {
        produtos = ArrayList()
        spinnerProducts = binding.spStockOutTextView
        val products = ArrayList<String?>()
        val databaseProducts = FirebaseDatabase.getInstance().getReference("products")
        databaseProducts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    produtos.add(product)
                    products.add(product!!.descricao)
                }
                val arrayAdapter = ArrayAdapter(
                    this@StockOutActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    products
                )
                spinnerProducts.setAdapter(arrayAdapter)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun stockOut() {
        val qtdStockOut = binding.etQtdStockOut.text.toString()
        val selected = selectedPosition
        val selectedProduct = produtos[selected]
        val id = selectedProduct?.id
        val description = selectedProduct?.descricao
        val databaseProducts = FirebaseDatabase.getInstance().getReference("products").child(
            id!!
        )
        val databaseHistory = FirebaseDatabase.getInstance().getReference("history")
        val mCalendar = Calendar.getInstance()
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val today = date.format(mCalendar.time)
        if (!TextUtils.isEmpty(qtdStockOut) && !TextUtils.equals(qtdStockOut, "0")) {
            val qtd = qtdStockOut.toInt()
            val newQtd = selectedProduct.qtdEstoque - qtd
            if (newQtd >= 0) {
                val product = Product(id, description, newQtd, today)
                val history = History(description, qtd, today, "saida")
                databaseProducts.setValue(product)
                val historyId = databaseHistory.push().key
                databaseHistory.child(historyId!!).setValue(history)
                finish()
            } else {
                AlertDialog.Builder(this@StockOutActivity)
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.not_possible_remove_more))
                    .setPositiveButton(getString(R.string.ok), null).show()
            }
        } else {
            AlertDialog.Builder(this@StockOutActivity)
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.Type_largest_quantity))
                .setPositiveButton(getString(R.string.ok), null).show()
        }
    }
}