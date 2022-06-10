package ru.skillbox.a25_29_contentprovider.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbox.a25_29_contentprovider.R

class MainDialogFragment : DialogFragment() {

//    private var listener: MainDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view = inflater.inflate(R.layout.dialog_download_file, null)

        val inputURL = view.findViewById<TextInputEditText>(R.id.inputIdEditText)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
//                listener!!.passDownloadLink(
                    inputURL!!.text.toString()
//                )
            }
            .create()
    }

/*    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            parentFragment as MainDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement MainDialogListener")
        }
    }*/

/*    interface MainDialogListener {
        fun passDownloadLink(link: String)
    }*/
}