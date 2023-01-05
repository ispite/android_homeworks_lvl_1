package ru.skillbox.a32_36_materialdesign.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.data.Product
import ru.skillbox.a32_36_materialdesign.databinding.ItemProductBinding
import ru.skillbox.a32_36_materialdesign.utils.inflate

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflate(ItemProductBinding::inflate))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.bind(currentProduct)
    }

    override fun getItemCount(): Int = productList.size

    fun submitList(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            with(binding) {
                titleTextView.text = item.title
                descriptionTextView.text = item.description

                Glide.with(itemView)
                    .load(item.image)
                    .placeholder(R.drawable.ic_image)
                    .into(productImageView)
            }
        }
    }
}