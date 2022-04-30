package com.heixss.myspendings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.MonthlySpendingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_monthly_spendings.*
import java.util.*

@AndroidEntryPoint
class MonthlySpendingsFragment : Fragment(),
    GridAdapter.ItemClickListener {

    private val viewModel: MonthlySpendingsViewModel by viewModels()
    lateinit var adapter: GridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_spendings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GridAdapter(context, emptyList())
        adapter.setClickListener(this)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_monthlySpendingsFragment_to_addSpendingFragment)
        }
        viewModel.liveSpendings.observe(viewLifecycleOwner, Observer { spendings ->
            val monthYear = ArrayList<String>()
            spendings.forEach {
                monthYear.add(it.month.toString() + "/" + it.year)
            }
            val distinctMonthYear = monthYear.distinct()
            adapter.setItems(distinctMonthYear)
            rv_grid.adapter = adapter
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        val monthYear = adapter.getItem(position)
        val month = monthYear.subSequence(0, 1)
        val year = monthYear.subSequence(2, monthYear.length)
        findNavController().navigate(
            MonthlySpendingsFragmentDirections.actionMonthlySpendingsFragmentToSpendingListFragment(
                month.toString().toInt(),
                year.toString().toInt()
            )
        )
    }
}