package com.galego.fabricio.vendapp.ui.customerlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.db.entity.CustomerEntity

class CustomerListAdapter(
    private val customerList: List<CustomerEntity>
) : RecyclerView.Adapter<CustomerListAdapter.CustomerListViewHolder>() {

    var onItemClick: ((customer: CustomerEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.customer_list_item, parent, false)
        return CustomerListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerListViewHolder, position: Int) {
        holder.bind(customerList[position])
    }

    override fun getItemCount(): Int = customerList.size

    inner class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.customerlistitem_name_textview)
        private val textPhone =
            itemView.findViewById<TextView>(R.id.customerlistitem_phone_textview)

        fun bind(customer: CustomerEntity) {
            textName.text = customer.name
            textPhone.text = customer.phone
            itemView.setOnClickListener {
                onItemClick?.invoke(customer)
            }
        }
    }

}