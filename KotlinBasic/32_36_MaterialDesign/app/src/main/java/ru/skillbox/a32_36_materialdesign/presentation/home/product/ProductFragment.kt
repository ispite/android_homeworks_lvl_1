package ru.skillbox.a32_36_materialdesign.presentation.home.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.databinding.FragmentProductBinding

class ProductFragment : Fragment(R.layout.fragment_product) {

    private val binding by viewBinding(FragmentProductBinding::bind)
    private val args: ProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        with(binding) {
            val item = args.product
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.ic_image)
                .into(productImageView)

            titleTextView.text = item.title
            descriptionTextView.text = item.description
        }
    }
}