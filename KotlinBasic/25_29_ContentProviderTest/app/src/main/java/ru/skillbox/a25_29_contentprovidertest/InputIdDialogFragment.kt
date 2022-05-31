package ru.skillbox.a25_29_contentprovidertest

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.lang.ClassCastException

class InputIdDialogFragment(private val type: String) : DialogFragment() {
    private var listener: InputIdDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_get_course_by_id, null)

        val inputId = view.findViewById<TextInputEditText>(R.id.inputIdEditText)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                listener!!.passID(
                    inputId!!.text.toString().toLong(),
                    type = type
                )
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
          activity as InputIdDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException (context.toString() + "must implement InputIdDialogListener")
        }
    }

    interface InputIdDialogListener {
        fun passID(id: Long, type: String)
    }
}