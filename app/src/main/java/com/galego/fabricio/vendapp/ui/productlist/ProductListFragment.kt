package com.galego.fabricio.vendapp.ui.productlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.databinding.ProductListFragmentBinding
import com.galego.fabricio.vendapp.repository.ProductRepository
import com.galego.fabricio.vendapp.repository.ProductRepositoryImpl

class ProductListFragment : Fragment() {

    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.getInstance(requireContext()).productDao
                val repository: ProductRepository = ProductRepositoryImpl(dao)
                return ProductListViewModel(repository) as T
            }
        }
    }

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