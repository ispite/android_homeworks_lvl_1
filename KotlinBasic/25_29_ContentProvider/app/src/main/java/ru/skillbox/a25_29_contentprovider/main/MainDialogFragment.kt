package ru.skillbox.a25_29_contentprovider.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.skillbox.a25_29_contentprovider.R

class MainDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view = inflater.inflate(R.layout.dialog_download_file, null)

        val inputURL = view.findViewById<TextInputEditText>(R.id.inputIdEditText)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ -> }
            .create()
    }

}