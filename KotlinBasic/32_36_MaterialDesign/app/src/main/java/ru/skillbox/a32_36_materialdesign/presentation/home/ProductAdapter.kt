package ru.skillbox.a32_36_materialdesign.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.data.Product
import ru.skillbox.a32_36_materialdesign.databinding.ItemProductBinding
import ru.skillbox.a32_36_materialdesign.utils.inflate
import timber.log.Timber

class ProductAdapter(private val onClick: (id: Long) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflate(ItemProductBinding::inflate), onClick)
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

    class ProductViewHolder(private val binding: ItemProductBinding, onClick: (id: Long) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentProductId: Long? = null

        init {
            Timber.d("currentProductId=$currentProductId")
            binding.root.setOnClickListener {
                Timber.d("clicked")
                currentProductId?.let(onClick)
            }
        }

        fun bind(item: Product) {
            currentProductId = item.id
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