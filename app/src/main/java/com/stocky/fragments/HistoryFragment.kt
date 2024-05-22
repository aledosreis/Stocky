package com.stocky.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stocky.adapters.HistoryAdapter
import com.stocky.model.History
import com.stocky.R
import com.stocky.databinding.FragmentHistoryBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    lateinit var histories: ArrayList<History?>
    lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        loadHistory()
    }

    private fun loadHistory() {
        val mCalendar = Calendar.getInstance()
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        mCalendar.add(Calendar.DATE, -30)
        var lastMonth: Date? = null
        try {
            lastMonth = date.parse(date.format(mCalendar.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val historyList = binding.listHistory
        histories = ArrayList()
        val databaseHistory = FirebaseDatabase.getInstance().getReference("history")
        databaseHistory.keepSynced(true)
        databaseHistory.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                histories.clear()
                for (historySnapshot in snapshot.children) {
                    val history = historySnapshot.getValue(
                        History::class.java
                    )
                    var productDate: Date? = null
                    try {
                        productDate = date.parse(history?.date!!)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    if (productDate!!.after(lastMonth)) {
                        histories.add(history)
                    }
                }
                histories.reverse()
                adapter = HistoryAdapter(histories)
                historyList.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val layoutManager = LinearLayoutManager(requireContext())
        historyList.layoutManager = layoutManager
    }
}