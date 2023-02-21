package com.galego.fabricio.vendapp.ui.customerlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.databinding.CustomerListFragmentBinding
import org.koin.android.ext.android.inject

class CustomerListFragment : Fragment() {

    private var _binding: CustomerListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomerListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.allCustomersEventData.observe(viewLifecycleOwner) { customers ->
            val customerAdapter = CustomerListAdapter(customers)
            customerAdapter.apply {
                onItemClick = { customer ->
                    val directions =
                        CustomerListFragmentDirections.actionCustomerListFragmentToCustomerFragment(
                            customer
                        )
                    findNavController().navigate(directions)
                }
            }
            binding.fragmentCustomerlistRecyclerview.run {
                setHasFixedSize(true)
                adapter = customerAdapter
            }
        }
    }

    private fun setListeners() {
        binding.fragmentCustomerlistAddFab.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerFragment)
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
}