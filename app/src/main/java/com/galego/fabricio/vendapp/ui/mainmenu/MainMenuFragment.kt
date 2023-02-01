package com.galego.fabricio.vendapp.ui.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.fragmentMainmenuProductsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_productListFragment)
        }
        binding.fragmentMainmenuCustomersButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_customerListFragment)
        }
        binding.fragmentMainmenuOrdersButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_orderListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}