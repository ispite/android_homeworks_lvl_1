package ru.skillbox.a16_lists_1

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class NewVehicleDialogFragment : DialogFragment() {
    private var listener: NewVehicleDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_add_vehicle, null)

        val brandEditText = view.findViewById<EditText>(R.id.brandEditText)
        val modelEditText = view.findViewById<EditText>(R.id.modelEditText)
        val URLEditText = view.findViewById<EditText>(R.id.URLEditText)
        val SDLEditText = view.findViewById<EditText>(R.id.SelfDrivingLevelEditText)

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                listener!!.passArguments(
                    brandEditText!!.text.toString(),
                    modelEditText!!.text.toString(),
                    URLEditText!!.text.toString(),
                    SDLEditText!!.text.toString()
                )
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            parentFragment as NewVehicleDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() + "must implement ExampleDialogListener"
            )
        }
    }

    interface NewVehicleDialogListener {
        fun passArguments(brand: String?, model: String?, URL: String?, SDL: String?)
    }
}