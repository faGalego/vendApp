package com.galego.fabricio.vendapp.ui.orderlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.databinding.OrderListFragmentBinding
import org.koin.android.ext.android.inject

class OrderListFragment : Fragment() {

    private var _binding: OrderListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.allOrdersEventData.observe(viewLifecycleOwner) { orders ->
            val orderAdapter = OrderListAdapter(orders)
            orderAdapter.apply {
                onItemClick = { order ->
                    val directions =
                        OrderListFragmentDirections.actionOrderListFragmentToOrderFragment(order)
                    findNavController().navigate(directions)
                }
            }
            binding.fragmentOrderlistRecyclerview.run {
                setHasFixedSize(true)
                adapter = orderAdapter
            }
        }
    }

    private fun setListeners() {
        binding.fragmentOrderlistAddFab.setOnClickListener {
            findNavController().navigate(R.id.action_orderListFragment_to_orderFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllOrders()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}