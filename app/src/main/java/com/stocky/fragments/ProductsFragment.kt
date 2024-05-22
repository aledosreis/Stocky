package com.stocky.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stocky.R
import com.stocky.activities.NewProductActivity
import com.stocky.activities.StockInActivity
import com.stocky.activities.StockOutActivity
import com.stocky.adapters.ProductsAdapter
import com.stocky.databinding.FragmentProductsBinding
import com.stocky.model.Product


class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private lateinit var products: ArrayList<Product?>
    private var sortBy: Int = 0
    private lateinit var preferences: SharedPreferences
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsBinding.bind(view)

        preferences = activity?.getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)!!

        sortBy = preferences.getInt(KEY_ORDER_BY, ORDER_BY_PRODUCT_NAME_ASC)

        loadProducts()
        handleClickButtons()
    }

    private fun loadProducts() {
        val productList = binding.listProducts
        products = ArrayList()
        val databaseProducts = FirebaseDatabase.getInstance().getReference("products")
        databaseProducts.keepSynced(true)
        databaseProducts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    products.add(product)
                }
                sortProductList()
                adapter = ProductsAdapter(products)
                productList.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val layoutManager = LinearLayoutManager(requireContext())
        productList.layoutManager = layoutManager
    }

    private fun sortProductList() {
        when (sortBy) {
            ORDER_BY_PRODUCT_NAME_ASC -> products.sortBy { it?.descricao }
            ORDER_BY_PRODUCT_NAME_DESC -> {
                products.sortBy { it?.descricao }
                products.reverse()
            }
            ORDER_BY_DATE_CHANGE_ASC -> products.sortBy { it?.dt_alteracao }
            ORDER_BY_DATE_CHANGE_DESC -> {
                products.sortBy { it?.dt_alteracao }
                products.reverse()
            }
            ORDER_BY_PRODUCT_QTD_ASC -> products.sortBy { it?.qtdEstoque }
            ORDER_BY_PRODUCT_QTD_DESC -> {
                products.sortBy { it?.qtdEstoque }
                products.reverse()
            }
        }
    }

    private fun handleClickButtons() {
        lateinit var intent : Intent
        binding.btnStockIn.setOnClickListener {
            intent = Intent(requireContext(), StockInActivity::class.java)
            startActivity(intent)
        }
        binding.btnStockOut.setOnClickListener {
            intent = Intent(requireContext(), StockOutActivity::class.java)
            startActivity(intent)
        }
        binding.btnNewProduct.setOnClickListener {
            intent = Intent(requireContext(), NewProductActivity::class.java)
            startActivity(intent)
        }
        binding.ivSort.setOnClickListener {
            val popupMenu = PopupMenu(activity?.applicationContext, it)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.order_by_name_asc -> {
                        sortBy = ORDER_BY_PRODUCT_NAME_ASC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    R.id.order_by_name_desc -> {
                        sortBy = ORDER_BY_PRODUCT_NAME_DESC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    R.id.order_by_qtd_asc -> {
                        sortBy = ORDER_BY_PRODUCT_QTD_ASC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    R.id.order_by_qtd_desc -> {
                        sortBy = ORDER_BY_PRODUCT_QTD_DESC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    R.id.order_by_date_asc -> {
                        sortBy = ORDER_BY_DATE_CHANGE_ASC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    R.id.order_by_date_desc -> {
                        sortBy = ORDER_BY_DATE_CHANGE_DESC
                        updateSortPreferences()
                        sortProductList()
                        adapter.notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.sort_menu)
            popupMenu.show()
        }
    }

    private fun updateSortPreferences() {
        val editor = preferences.edit()
        editor.putInt(KEY_ORDER_BY, sortBy)
        editor.apply()
    }

    companion object {
        private const val ORDER_BY_PRODUCT_NAME_ASC = 1
        private const val ORDER_BY_PRODUCT_NAME_DESC = 2
        private const val ORDER_BY_DATE_CHANGE_ASC = 3
        private const val ORDER_BY_DATE_CHANGE_DESC = 4
        private const val ORDER_BY_PRODUCT_QTD_ASC = 5
        private const val ORDER_BY_PRODUCT_QTD_DESC = 6

        private const val KEY_SHARED_PREFERENCES_NAME = "STOCKY"
        private const val KEY_ORDER_BY = "ORDER_BY"
    }
}