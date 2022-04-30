package com.heixss.myspendings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.AddSubSpendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_sub_spending.*

@AndroidEntryPoint
class AddSubSpendingFragment : Fragment() {

    private val viewModel: AddSubSpendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.categoryId = AddSubSpendingFragmentArgs.fromBundle(it).categoryId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_sub_spending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddSubSpending.setOnClickListener {
            if (edSubSpendingDesc.text.toString().isEmpty() || etSubSpendingValue.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val timestamp: Long = System.currentTimeMillis()
            viewModel.addSpending(
                viewModel.categoryId,
                edSubSpendingDesc.text.toString().trim(),
                etSubSpendingValue.text.toString().toDouble(),
                timestamp
            )
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

    }
}