package com.heixss.myspendings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.heixss.myspendings.R
import com.heixss.myspendings.viewmodel.AddSpendingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_spending.*

@AndroidEntryPoint
class AddSpendingFragment : Fragment() {

    private val viewModel: AddSpendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdauga.setOnClickListener {
            if (etCategory.text.toString().isEmpty() || etDescription.text.toString().isEmpty() || etValue.text.toString().isEmpty()) {
                Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val timestamp: Long = System.currentTimeMillis()
            viewModel.addSpending(
                etCategory.text.toString().trim().toLowerCase(),
                etDescription.text.toString().trim(),
                etValue.text.toString().toDouble(),
                timestamp
            )
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}