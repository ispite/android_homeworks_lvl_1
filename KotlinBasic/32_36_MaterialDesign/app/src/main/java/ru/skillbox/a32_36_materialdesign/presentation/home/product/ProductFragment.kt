package ru.skillbox.a32_36_materialdesign.presentation.home.product

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.databinding.FragmentProductBinding
import ru.skillbox.a32_36_materialdesign.utils.themeColor

class ProductFragment : Fragment(R.layout.fragment_product) {

    private val binding by viewBinding(FragmentProductBinding::bind)
    private val args: ProductFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
//            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        with(binding) {
            val item = args.product
            // DataBind
            productItem = item
            //////////////////

            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(R.drawable.ic_image)
                .into(productImageView)

            titleTextView.text = item.title
            descriptionTextView.text = item.description
        }
    }
}