package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        idTextView.text = args.vehicleID.toString()

        val displayMetrics = DisplayMetrics()
        val width = displayMetrics.widthPixels

        carPhotoDetailedImageView.layoutParams.height = width
        Glide.with(requireContext())
            .load(args.vehiclePhoto)
            .placeholder(R.drawable.ic_image)
            .into(carPhotoDetailedImageView)

        idTrueTextView.text = args.trueIDnav.toString()
    }


}