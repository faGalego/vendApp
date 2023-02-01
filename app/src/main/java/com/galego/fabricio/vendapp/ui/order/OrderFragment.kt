package com.galego.fabricio.vendapp.ui.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.databinding.OrderFragmentBinding
import com.galego.fabricio.vendapp.repository.CustomerRepositoryImpl
import com.galego.fabricio.vendapp.repository.OrderRepositoryImpl

class OrderFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: OrderFragmentBinding? = null
    private val binding get() = _binding!!

    val args: OrderFragmentArgs by navArgs()

    private val viewModel: OrderViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val orderDao = AppDatabase.getInstance(requireContext()).orderDao
                val customerDao = AppDatabase.getInstance(requireContext()).customerDao

                val orderRepository = OrderRepositoryImpl(orderDao)
                val customerRepository = CustomerRepositoryImpl(customerDao)

                return OrderViewModel(orderRepository, customerRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgs()
        setObservers()
        setListeners()
    }

    private fun setArgs() {
        args.order?.let { order ->
//            binding.fragmentOrderCustomerSpinner.setText(order.name)
        }
    }

    private fun setObservers() {

        viewModel.allCustomersEventData.observe(viewLifecycleOwner) { customers ->
            val partnersAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, customers)
            partnersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fragmentOrderCustomerSpinner.adapter = partnersAdapter
        }

        viewModel.orderStateEventData.observe(viewLifecycleOwner) { state ->
            when (state) {
                OrderViewModel.OrderState.Inserted,
                OrderViewModel.OrderState.Updated -> {
                    clearFields()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.orderMessageEventData.observe(viewLifecycleOwner) { messageId ->
            Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
        }

    }

    private fun clearFields() {
        //..
    }

    private fun setListeners() {

        binding.fragmentOrderCustomerSpinner.onItemSelectedListener = this

        binding.fragmentOrderSaveButton.setOnClickListener {
            //pegar os campos dos componentes..
            viewModel.insertOrUpdateOrder(99.9, args.order?.id ?: 0)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCustomers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.id) {
            R.id.fragment_order_customer_spinner -> {
                viewModel.setSelectedCustomer(position)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}