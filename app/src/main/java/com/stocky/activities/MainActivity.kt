package com.stocky.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stocky.R
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.stocky.BuildConfig
import com.stocky.databinding.ActivityMainBinding
import com.stocky.fragments.HistoryFragment
import com.stocky.fragments.OrdersFragment
import com.stocky.fragments.ProductsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private var currentVersion = 0f
    private lateinit var bottomNavigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        currentVersion = BuildConfig.VERSION_NAME.toFloat()

        val preferences = getSharedPreferences(KEY_SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val lastVersionChangelog = preferences.getFloat(KEY_LATEST_VERSION, 1.0f)

        if (lastVersionChangelog != currentVersion) {
            val editor = preferences.edit()
            editor.putFloat(KEY_LATEST_VERSION, currentVersion)
            editor.apply()
            showChangelog()
        }

        val productsFragment = ProductsFragment()
        val historyFragment = HistoryFragment()
        val ordersFragment = OrdersFragment()

        changeCurrentFragment(productsFragment)

        bottomNavigation = binding.bottomNavigation
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.products -> changeCurrentFragment(productsFragment)
                R.id.history -> changeCurrentFragment(historyFragment)
                R.id.listbuy -> changeCurrentFragment(ordersFragment)
            }
            true
        }
    }

    private fun changeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    private fun showChangelog() {
        val changelogMessage = """
            - Realizado pequenos ajustes visuais.
            - Adicionado funcionalidade para ordenar a listagem dos produtos cadastrados: por quantidade, por nome e por data
            - Adicionado a funcionalidade de trabalhar com a aplicação mesmo sem conexao com a internet, ao ser estabelecida conexão os dados são sincronizados.
            """.trimIndent()

        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(getString(R.string.Update_notes_title))
            .setIcon(R.drawable.ic_info)
            .setMessage(changelogMessage)
            .setCancelable(false)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.products) {
            super.onBackPressed()
            finish()
        } else bottomNavigation.selectedItemId = R.id.products
    }

    companion object {
        private const val KEY_LATEST_VERSION = "lastVersionChangelog"
        private const val KEY_SHARED_PREFERENCES_NAME = "STOCKY"
    }
}