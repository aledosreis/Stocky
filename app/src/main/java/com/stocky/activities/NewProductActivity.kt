package com.stocky.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import android.text.TextUtils
import com.stocky.model.Product
import android.widget.Toast
import com.stocky.R
import com.stocky.databinding.ActivityNewProductBinding
import java.text.SimpleDateFormat
import java.util.*

class NewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddProduct.setOnClickListener {
            addNewProduct()
        }
    }

    private fun addNewProduct() {
        val databaseProducts = FirebaseDatabase.getInstance().getReference("products")
        val mCalendar = Calendar.getInstance()
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val today = date.format(mCalendar.time)
        val newProductName = binding.etNewProduct.text.toString()
        if (!TextUtils.isEmpty(newProductName)) {
            val productId = databaseProducts.push().key
            val product = Product(productId, newProductName, 0, today)
            databaseProducts.child(productId!!).setValue(product)
            Toast.makeText(this, getString(R.string.product_registered), Toast.LENGTH_LONG).show()
            finish()
        } else {
            AlertDialog.Builder(this@NewProductActivity)
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.type_product_name))
                .setPositiveButton(getString(R.string.ok), null).show()
        }
    }
}