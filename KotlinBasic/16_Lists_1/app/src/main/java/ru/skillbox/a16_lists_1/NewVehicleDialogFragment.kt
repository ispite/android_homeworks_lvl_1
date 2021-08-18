package ru.skillbox.a16_lists_1

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_vehicle.*

class NewVehicleDialogFragment:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_add_vehicle)
            .setPositiveButton("ok") { _,_, ->
//                brandEditText.text.toString()
                Toast.makeText(requireContext(), brandEditText.text.toString(), Toast.LENGTH_SHORT).show()

//                modelEditText.text.toString()
//                URLEditText.text.toString()
//                SelfDrivingLevelEditText.text.toString()
            }
            .show()

    }
}