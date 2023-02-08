package com.galego.fabricio.vendapp.ui.orderlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.wrapper.OrderCustomer

class OrderListAdapter(
    private val ordersList: List<OrderCustomer>
) : RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>() {

    var onItemClick: ((order: OrderCustomer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return OrderListViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(ordersList[position])
    }

    override fun getItemCount(): Int = ordersList.size

    inner class OrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textCustomer = itemView.findViewById<TextView>(R.id.orderlistitem_name_textview)
        private val textTotal = itemView.findViewById<TextView>(R.id.orderlistitem_total_textview)

        fun bind(order: OrderCustomer) {
            textCustomer.text = order.customerName
            textTotal.text = Converters.doubleToMoneyString(order.orderTotal)
            itemView.setOnClickListener {
                onItemClick?.invoke(order)
            }
        }

    }

}