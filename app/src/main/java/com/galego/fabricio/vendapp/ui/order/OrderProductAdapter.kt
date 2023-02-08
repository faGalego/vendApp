package com.galego.fabricio.vendapp.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity

class OrderProductAdapter(
    private val products: List<OrderProductEntity>
) : RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_product_item, parent, false)
        return OrderProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class OrderProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.order_product_name_textview)
        private val textQuantity =
            itemView.findViewById<TextView>(R.id.order_product_quantity_textview)
        private val textPrice = itemView.findViewById<TextView>(R.id.order_product_price_textview)
        private val textTotal = itemView.findViewById<TextView>(R.id.order_product_total_textview)

        fun bind(product: OrderProductEntity) {
            textName.text = product.productId.toString()
            textQuantity.text = product.quantity.toString()
            textPrice.text = Converters.doubleToMoneyString(product.price)
            textTotal.text = Converters.doubleToMoneyString(product.total)
        }
    }

}