package com.demo.productsmarket.ui.home

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.demo.productsmarket.R
import com.demo.productsmarket.ui.home.model.ViewProduct
import com.demo.productsmarket.utils.dpToPixel
import com.demo.productsmarket.utils.loadUrl


internal class ProductsAdapter(
    private val list: List<ViewProduct>,
    private val onItemClick: ((ViewProduct) -> Unit)
) : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            (parent.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.single_product_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        val context = holder.itemView.context
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = if (position == 0) context.dpToPixel(16).toInt() else context.dpToPixel(6).toInt()
        holder.titleTv.text = item.name
        holder.priceTv.text = item.price
        holder.productIv.loadUrl(item.image.link)
        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.productIv.layoutParams.height = item.image.height
        val cellWidth = Resources.getSystem().displayMetrics.widthPixels - context.dpToPixel(32)
        val imageHeight = cellWidth * item.image.height / item.image.width
        holder.productIv.layoutParams.height = imageHeight.toInt()

    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.titleTv)
        val priceTv: TextView = itemView.findViewById(R.id.priceTv)
        val productIv: ImageView = itemView.findViewById(R.id.productIv)

    }

    override fun getItemCount(): Int {
        return list.size
    }


}
