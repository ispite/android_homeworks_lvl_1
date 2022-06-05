package ru.skillbox.a25_29_contentprovidertest

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText

class UpdateCourseByIdDialogFragment : DialogFragment() {
    private var listener: UpdateCourseByIdDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_update_course_by_id, null)

        val inputId = view.findViewById<TextInputEditText>(R.id.inputUpdateIdEditText)
        val updateCourseTitle = view.findViewById<TextInputEditText>(R.id.inputUpdateTitleEditText)
        val updateCourseDuration = view.findViewById<TextInputEditText>(R.id.inputUpdateDurationEditText)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                listener!!.passUpdateArgs(
                    inputId.text.toString().toLong(),
                    updateCourseTitle.text.toString(),
                    updateCourseDuration.text.toString().toLong()
                )
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            activity as UpdateCourseByIdDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException (context.toString() + "must implement UpdateCourseByIdDialogListener")
        }
    }

    interface UpdateCourseByIdDialogListener {
        fun passUpdateArgs(id: Long, title: String, duration: Long)
    }
}