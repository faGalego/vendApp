package com.galego.fabricio.vendapp.ui.productlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.databinding.ProductListFragmentBinding
import org.koin.android.ext.android.inject

class ProductListFragment : Fragment() {

    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.allProductsEvent.observe(viewLifecycleOwner) { allProducts ->
            val productAdapter = ProductListAdapter(allProducts)
            productAdapter.apply {
                onItemClick = { product ->
                    val directions =
                        ProductListFragmentDirections.actionProductListFragmentToProductFragment(
                            product
                        )
                    findNavController().navigate(directions)
                }
            }
            binding.fragmentProductlistRecyclerview.run {
                setHasFixedSize(true)
                adapter = productAdapter
            }
        }
    }

    private fun setListeners() {
        binding.fragmentProductlistAddFab.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_productFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}