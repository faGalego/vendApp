package com.galego.fabricio.vendapp.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.galego.fabricio.vendapp.R
import com.galego.fabricio.vendapp.data.common.Converters
import com.galego.fabricio.vendapp.data.db.entity.ProductEntity

class ProductListAdapter(
    private val productList: List<ProductEntity>
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    var onItemClick: ((product: ProductEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewName: TextView =
            itemView.findViewById(R.id.productlistitem_name_textview)
        private val textViewPrice: TextView =
            itemView.findViewById(R.id.productlistitem_price_textview)

        fun bind(product: ProductEntity) {
            textViewName.text = product.name
            textViewPrice.text = Converters.doubleToMoneyString(product.price)
            itemView.setOnClickListener {
                onItemClick?.invoke(product)
            }
        }
    }

}