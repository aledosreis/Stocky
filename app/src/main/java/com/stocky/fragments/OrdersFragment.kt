package com.stocky.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stocky.adapters.OrdersAdapter
import com.stocky.model.Order
import com.stocky.model.Product
import com.stocky.R
import com.stocky.databinding.FragmentOrdersBinding
import java.util.*

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    lateinit var spinnerProduct: AutoCompleteTextView
    private lateinit var orders: ArrayList<Order>
    private lateinit var adapter: OrdersAdapter
    private lateinit var myWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersBinding.bind(view)
        loadOrdersList()
        loadProductsInSpinner()
        addNewOrder()
        printOrder()
    }

    private fun loadOrdersList() {
        val listOrders = binding.listOrders
        orders = ArrayList()
        adapter = OrdersAdapter(orders)
        val layoutManager = LinearLayoutManager(requireContext())
        listOrders.layoutManager = layoutManager
        listOrders.adapter = adapter
    }

    private fun loadProductsInSpinner() {
        spinnerProduct = binding.tvSpinnerProducts
        val products = ArrayList<String?>()
        val databaseProducts = FirebaseDatabase.getInstance().getReference("products")
        databaseProducts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) {
                    return
                }
                products.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    products.add(product!!.descricao)
                }
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    products
                )
                spinnerProduct.setAdapter(arrayAdapter)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun addNewOrder() {
        binding.btnNewOrder.setOnClickListener {
            val orderQuantity = binding.etOrderQuantity.text.toString()
            val selectedProduct = spinnerProduct.text.toString()
            if (orderQuantity == "" || orderQuantity.toInt() == 0) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.Type_largest_quantity),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newOrder = Order(selectedProduct, orderQuantity.toInt())
                for (order in orders) {
                    if (order.produto == newOrder.produto) {
                        order.quantidade = newOrder.quantidade
                        adapter.notifyItemChanged(orders.indexOf(order))
                        return@setOnClickListener
                    }
                }
                orders.add(newOrder)
                adapter.notifyItemInserted(orders.size)
            }
        }
    }

    private fun printOrder() {
        binding.btnPrintOrder.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                val webView = WebView(requireContext())
                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        return false
                    }

                    override fun onPageFinished(view: WebView, url: String) {
                        createWebPrintJob(view)
                    }
                }
                val htmlDoc = getDocument()
                webView.loadDataWithBaseURL(
                    null, htmlDoc,
                    "text/HTML", "UTF-8", null
                )
                myWebView = webView
            }
        }
    }

    private fun getDocument() : String {
        val monthName = arrayOf(
            "Janeiro", "Fevereiro",
            "Março", "Abril", "Maio", "Junho", "Julho",
            "Agosto", "Setembro", "Outubro", "Novembro",
            "Dezembro"
        )
        val cal = Calendar.getInstance()
        val month = monthName[cal[Calendar.MONTH]]
        var htmlDocument = "<html><head><style>#title {" +
                "      text-align: center;" +
                "      font-family: arial, sans-serif;" +
                "    }" +
                "    #pedidos {" +
                "      text-align: center;" +
                "      font-family: Arial, sans-serif;" +
                "      border-collapse: collapse;" +
                "      border: 3px solid #ddd;" +
                "      width: 100%;" +
                "    }" +
                "    #pedidos td, #pedidos th {" +
                "      border: 1px solid #ddd;" +
                "      padding: 8px;" +
                "    }" +
                "    #pedidos tr:nth-child(even) {" +
                "      background-color: #f2f2f2;" +
                "    }" +
                "    #pedidos th {" +
                "      padding-top: 12px;" +
                "      padding-bottom: 12px;" +
                "      text-align: center;" +
                "      background-color: #4caf50;" +
                "      color: #fff;" +
                "    }</style>"
        htmlDocument += "<body><div><h1 id='title'>Pedidos do mês de $month</h1>"
        htmlDocument += "<table id='pedidos'><tbody><tr><th>Produto</th><th>Qtd</th></tr>"
        for (order in orders) {
            val products = order.produto
            val quantity = order.quantidade.toString()
            htmlDocument += "<tr><td>$products</td><td>$quantity</td></tr>"
        }
        htmlDocument += "</tbody></table></div></body></html>"
        return htmlDocument
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = requireContext()
            .getSystemService(AppCompatActivity.PRINT_SERVICE) as PrintManager
        val printAdapter = webView.createPrintDocumentAdapter("Stocky_Order")
        val jobName = "${getString(R.string.app_name)} Print"
        printManager.print(
            jobName, printAdapter,
            PrintAttributes.Builder().build()
        )
    }
}