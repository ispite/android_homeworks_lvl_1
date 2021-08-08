package ru.skillbox.fragments_15

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class ArticleFragment : Fragment(R.layout.fragment_article_page) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val args = requireArguments()
        view?.apply {
            findViewById<ImageView>(R.id.imageView).setImageResource(
                args.getInt(KEY_IMAGE)
            )
            findViewById<TextView>(R.id.textViewCaption).text = resources.getString(
                args.getInt(
                    KEY_CAPTION
                )
            )
            findViewById<TextView>(R.id.textViewText).text = resources.getString(
                args.getInt(
                    KEY_TEXT
                )
            )
        }
        return view
    }

    companion object {

        private const val KEY_IMAGE = "image"
        private const val KEY_CAPTION = "caption"
        private const val KEY_TEXT = "text"
        private const val KEY_TYPE = "type"

        fun newInstance(
            @DrawableRes drawableRes: Int,
            @StringRes captionRes: Int,
            @StringRes textRes: Int,
            type: ArticleTypes
        ): ArticleFragment {
            return ArticleFragment().withArguments {
                putInt(KEY_IMAGE, drawableRes)
                putInt(KEY_CAPTION, captionRes)
                putInt(KEY_TEXT, textRes)
                putSerializable(KEY_TYPE, type)
            }
        }
    }
}