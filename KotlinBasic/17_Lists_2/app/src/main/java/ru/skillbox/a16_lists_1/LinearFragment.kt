package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


const val ARG_OBJECT = "object"

class LinearFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_linear, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.linearTextView)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }

}

/*
class LinearFragment:Fragment(R.layout.fragment_linear) {

    private var mPage = 0

    companion object {
        val ARG_PAGE = "ARG_PAGE"
        fun newInstance(page: Int): LinearFragment? {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = LinearFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPage = requireArguments().getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_linear, container, false)
        val textView = view as TextView
        textView.text = "Fragment #$mPage"
        return view
    }
}*/
