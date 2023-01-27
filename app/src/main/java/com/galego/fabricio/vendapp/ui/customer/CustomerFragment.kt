package com.galego.fabricio.vendapp.ui.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.databinding.CustomerFragmentBinding
import com.galego.fabricio.vendapp.repository.CustomerRepositoryImpl

class CustomerFragment : Fragment() {

    private var _binding: CustomerFragmentBinding? = null
    private val binding get() = _binding!!

    val args: CustomerFragmentArgs by navArgs()

    private val viewModel: CustomerViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.getInstance(requireContext()).customerDao
                val repository = CustomerRepositoryImpl(dao)
                return CustomerViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgs()
        setObservers()
        setListeners()
    }

    private fun setArgs() {
        args.customer?.let { customer ->
            binding.fragmentCustomerNameEditText.setText(customer.name)
            binding.fragmentCustomerPhoneEditText.setText(customer.phone)
        }
    }

    private fun setObservers() {
        viewModel.customerStateEventData.observe(viewLifecycleOwner) { customerState ->
            when (customerState) {
                CustomerViewModel.CustomerState.Inserted,
                CustomerViewModel.CustomerState.Updated -> {
                    clearFields()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.customerMessageEventData.observe(viewLifecycleOwner) { messageId ->
            Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.fragmentCustomerNameEditText.text?.clear()
        binding.fragmentCustomerPhoneEditText.text?.clear()
    }

    private fun setListeners() {
        binding.fragmentCustomerSaveButton.setOnClickListener {
            val name = binding.fragmentCustomerNameEditText.text.toString().trim()
            val phone = binding.fragmentCustomerPhoneEditText.text.toString().trim()
            viewModel.insertOrUpdateCustomer(name, phone, args.customer?.id ?: 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}