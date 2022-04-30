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
import com.heixss.myspendings.viewmodel.SpendingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_spending_list.*

@AndroidEntryPoint
class SpendingsByCategoryFragment : Fragment(), ListAdapter.ItemClickListener {

    private val viewModel: SpendingListViewModel by viewModels()
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle: Bundle ->
            viewModel.month = SpendingsByCategoryFragmentArgs.fromBundle(bundle).month
            viewModel.year = SpendingsByCategoryFragmentArgs.fromBundle(bundle).year
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { ctx ->
            adapter = ListAdapter(ctx, emptyList())
            rvSpendingList.adapter = adapter
            adapter.setClickListener(this)
        }
        viewModel.loadTotalCategorySpendings(viewModel.month, viewModel.year)
        viewModel.liveTotalCategorySpendings.observe(viewLifecycleOwner, Observer {
            context?.let { ctx ->
                var sum = 0.0
                it.forEach {
                    sum+=it.totalSum
                }
                tvtotal.text = sum.toString()
                adapter.setList(it)
            }
        })
    }

    override fun onItemClick(view: View?, position: Int) {
        val totalCategorySpendings = adapter.getItem(position)
        val categoryId = totalCategorySpendings.category.id
        val month = viewModel.month
        val year = viewModel.year
        findNavController().navigate(
            SpendingsByCategoryFragmentDirections.actionSpendingListFragmentToDetailedSpendingsFragment(
                month.toString().toInt(),
                year.toString().toInt(),
                categoryId
            )
        )
    }
}