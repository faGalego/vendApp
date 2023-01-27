package com.galego.fabricio.vendapp.ui.product

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.common.MoneyTextWatcher
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.databinding.ProductFragmentBinding
import com.galego.fabricio.vendapp.repository.ProductRepository
import com.galego.fabricio.vendapp.repository.ProductRepositoryImpl

class ProductFragment : Fragment() {

    private var _binding: ProductFragmentBinding? = null
    private val binding get() = _binding!!

    val args: ProductFragmentArgs by navArgs()

    private val viewModel: ProductViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val productDao = AppDatabase.getInstance(requireContext()).productDao
                val productRepository: ProductRepository = ProductRepositoryImpl(productDao)

                return ProductViewModel(productRepository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgs()
        setObservers()
        setListeners()
    }

    private fun setArgs() {
        binding.fragmentProductPriceEditText.setText(Converters.doubleToMoneyString(0.0))
        args.product?.let { product ->
            binding.fragmentProductNameEditText.setText(product.name)
            binding.fragmentProductPriceEditText.setText(Converters.doubleToMoneyString(product.price))
        }
    }

    private fun setObservers() {

        viewModel.productStateEventData.observe(viewLifecycleOwner) { productState ->
            when (productState) {
                is ProductViewModel.ProductState.Inserted,
                ProductViewModel.ProductState.Updated -> {
                    clearFields()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.productMessageEventData.observe(viewLifecycleOwner) { messageId ->
            Toast.makeText(context, messageId, Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        binding.fragmentProductNameEditText.text?.clear()
        binding.fragmentProductPriceEditText.text?.clear()
    }

    private fun setListeners() {

        binding.fragmentProductPriceEditText.addTextChangedListener(MoneyTextWatcher(binding.fragmentProductPriceEditText))

        binding.fragmentProductSaveButton.setOnClickListener {
            val name = binding.fragmentProductNameEditText.text.toString().trim()
            val price = Converters.moneyStringToDouble(
                binding.fragmentProductPriceEditText.text.toString().trim()
            )

            viewModel.insertOrUpdateProduct(name, price, args.product?.id ?: 0)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}