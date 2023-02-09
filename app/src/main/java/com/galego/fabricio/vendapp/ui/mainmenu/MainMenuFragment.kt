package com.galego.fabricio.vendapp.ui.mainmenu

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.AppDatabase
import com.galego.fabricio.vendapp.data.db.wrapper.MonthWithTotal
import com.galego.fabricio.vendapp.databinding.FragmentMainMenuBinding
import com.galego.fabricio.vendapp.repository.OrderRepositoryImpl
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainMenuViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dao = AppDatabase.getInstance(requireContext()).orderDao
                val repository = OrderRepositoryImpl(dao)
                return MainMenuViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        setObservers()
        setListeners()
    }

    private fun initComponents() {
        initOrdersBarChart()
    }

    private fun initOrdersBarChart() {
        binding.fragmentMainmenuOrdersChart.description.isEnabled = false
        binding.fragmentMainmenuOrdersChart.setMaxVisibleValueCount(10)
        binding.fragmentMainmenuOrdersChart.setPinchZoom(false)
        binding.fragmentMainmenuOrdersChart.setDrawBarShadow(false)
        binding.fragmentMainmenuOrdersChart.setDrawGridBackground(false)
        binding.fragmentMainmenuOrdersChart.axisLeft.setDrawGridLines(false)
        binding.fragmentMainmenuOrdersChart.animateY(1500)
        binding.fragmentMainmenuOrdersChart.legend.isEnabled = false
        binding.fragmentMainmenuOrdersChart.setNoDataTextColor(R.color.purple_700)
        binding.fragmentMainmenuOrdersChart.setNoDataText(getString(R.string.mainmenu_no_data_available_chart))

        binding.fragmentMainmenuOrdersChart.axisRight.isEnabled = false

        val xAxis = binding.fragmentMainmenuOrdersChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        binding.fragmentMainmenuOrdersChart.axisLeft.granularity = 1f
        binding.fragmentMainmenuOrdersChart.axisLeft.setDrawZeroLine(true) // draw a zero line
    }

    private fun setObservers() {
        viewModel.allTotalsByMonths.observe(viewLifecycleOwner) { monthsAndTotals ->
            if (monthsAndTotals.isNotEmpty()) {
                loadOrdersChart(monthsAndTotals)
            }
        }
    }

    private fun loadOrdersChart(monthsAndTotals: List<MonthWithTotal?>?) {
        val values = ArrayList<BarEntry>()
        val names = mutableListOf<String>()

        for (i in monthsAndTotals!!.indices) {
            names.add(monthsAndTotals[i]!!.month)
            values.add(BarEntry(i.toFloat(), monthsAndTotals[i]!!.total.toFloat()))
        }

        val formatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return names[value.toInt()]
            }
        }
        binding.fragmentMainmenuOrdersChart.xAxis.valueFormatter = formatter

        val set1 = BarDataSet(values, "Orders Data Set")
        set1.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return Converters.doubleToMoneyString(value.toDouble())
            }
        }
        set1.setDrawValues(true)
//        set1.color = Color.rgb(165, 214, 167)

        if ((monthsAndTotals.filter { it!!.total < 0 }).isEmpty()) {
            binding.fragmentMainmenuOrdersChart.axisLeft.axisMinimum = 0f; // start at zero
        }

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val data = BarData(dataSets)
        binding.fragmentMainmenuOrdersChart.data = data
        binding.fragmentMainmenuOrdersChart.setFitBars(true)

        binding.fragmentMainmenuOrdersChart.invalidate()
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

    override fun onResume() {
        super.onResume()
        viewModel.getAllTotalsByMonths()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}