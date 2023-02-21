package com.galego.fabricio.vendapp.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.entity.OrderProductEntity
import com.galego.fabricio.vendapp.data.db.wrapper.OrderProduct

class OrderProductAdapter(
    private val products: List<OrderProduct>
) : RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder>() {

    var onDeleteClick: ((orderProduct: OrderProduct) -> Unit)? = null
    var onOneMoreClick: ((orderProduct: OrderProduct) -> Unit)? = null
    var onOneLessClick: ((orderProduct: OrderProduct) -> Unit)? = null

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

        private val deleteImg =
            itemView.findViewById<ImageView>(R.id.order_product_delete_button)
        private val oneMoreButton =
            itemView.findViewById<Button>(R.id.order_product_one_more_button)
        private val oneLessButton =
            itemView.findViewById<Button>(R.id.order_product_one_less_button)


        fun bind(product: OrderProduct) {

            textName.text = product.product!!.name
            textQuantity.text = product.entity!!.quantity.toString()
            textPrice.text = Converters.doubleToMoneyString(product.entity!!.price)
            textTotal.text = Converters.doubleToMoneyString(product.entity!!.total)

            deleteImg.setOnClickListener { onDeleteClick?.invoke(product) }
            oneMoreButton.setOnClickListener { onOneMoreClick?.invoke(product) }
            oneLessButton.setOnClickListener { onOneLessClick?.invoke(product) }

        }
    }

}