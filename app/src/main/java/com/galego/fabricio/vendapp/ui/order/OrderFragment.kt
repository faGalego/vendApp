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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.databinding.OrderFragmentBinding
import com.galego.fabricio.vendapp.extension.hideKeyboard
import com.galego.fabricio.vendapp.repository.CustomerRepositoryImpl
import com.galego.fabricio.vendapp.repository.OrderProductRepositoryImpl
import com.galego.fabricio.vendapp.repository.OrderRepositoryImpl
import com.galego.fabricio.vendapp.repository.ProductRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: OrderFragmentBinding? = null
    private val binding get() = _binding!!

    val args: OrderFragmentArgs by navArgs()

    private val viewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setObservers() {

        viewModel.allCustomersEventData.observe(viewLifecycleOwner) { customers ->
            val customersAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, customers)
            customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fragmentOrderCustomerSpinner.adapter = customersAdapter
            args.order?.let { argsOrder ->
                binding.fragmentOrderCustomerSpinner.setSelection(customers.indexOfFirst {
                    it.id == argsOrder.customerId
                })
            }
        }

        viewModel.productsData.observe(viewLifecycleOwner) { products ->
            val productsAdapter = OrderProductAdapter(products)

            productsAdapter.onDeleteClick = { product -> viewModel.removeProduct(product) }
            productsAdapter.onOneMoreClick = { product -> viewModel.oneMore(product) }
            productsAdapter.onOneLessClick = { product -> viewModel.oneLess(product) }

            binding.fragmentOrderProductsRecyclerview.run {
                setHasFixedSize(true)
                adapter = productsAdapter
            }

        }

        viewModel.orderStateEventData.observe(viewLifecycleOwner) { state ->
            when (state) {
                OrderViewModel.OrderState.Inserted,
                OrderViewModel.OrderState.Updated -> {
                    clearFields()
                    hideKeyboard()
                    findNavController().popBackStack()
                }
                OrderViewModel.OrderState.TriedToInsertProduct -> clearFields()
            }
        }

        viewModel.orderMessageEventData.observe(viewLifecycleOwner) { messageId ->
            Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
        }

    }

    private fun clearFields() {
        binding.fragmentOrderProductEditText.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {

        binding.fragmentOrderCustomerSpinner.onItemSelectedListener = this

        binding.fragmentOrderAddProductButton.setOnClickListener {

            if (binding.fragmentOrderProductEditText.text?.isNotEmpty() == true) {
                viewModel.insertProduct(
                    binding.fragmentOrderProductEditText.text.toString().toLong()
                )
            }
        }

        binding.fragmentOrderSaveButton.setOnClickListener {
            viewModel.insertOrUpdateOrder(args.order?.orderId ?: 0)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCustomers()
        viewModel.getProductsByOrderId(args.order?.orderId ?: 0)
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