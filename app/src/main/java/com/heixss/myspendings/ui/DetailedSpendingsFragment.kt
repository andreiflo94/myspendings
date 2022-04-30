package com.heixss.myspendings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.DetailedSpendingsViewModel
import com.heixss.myspendings.viewmodel.VMSpending
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detailed_spending_list.*

@AndroidEntryPoint
class DetailedSpendingsFragment : Fragment() {

    private val viewModel: DetailedSpendingsViewModel by viewModels()
    private lateinit var adapter: SpendingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle: Bundle ->
            viewModel.month = DetailedSpendingsFragmentArgs.fromBundle(bundle).month
            viewModel.year = DetailedSpendingsFragmentArgs.fromBundle(bundle).year
            viewModel.categoryId = DetailedSpendingsFragmentArgs.fromBundle(bundle).categoryId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_spending_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { ctx ->
            btnAdd.setOnClickListener{
                findNavController().navigate(
                    DetailedSpendingsFragmentDirections.actionDetailedSpendingsFragmentToAddSubSpendingFragment(viewModel.categoryId))
            }
            adapter = SpendingListAdapter(ctx, emptyList())
            adapter.setClickListener(itemClickListener = object :
                SpendingListAdapter.ItemClickListener{
                override fun onItemClick(view: View?, position: Int) {

                }

                override fun onItemRemove(view: View?, vmSpending: VMSpending) {
                    viewModel.removeVmSpending(vmSpending.spending.id)
                    viewModel.loadVMSpendings(viewModel.categoryId, viewModel.month, viewModel.year)
                }

            })
            rvDetaildSpendings.adapter = adapter
        }
        viewModel.loadVMSpendings(viewModel.categoryId, viewModel.month, viewModel.year)
        viewModel.liveVMSpendings.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }
}