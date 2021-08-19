package ru.skillbox.a16_lists_1

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_vehicle.*

class NewVehicleDialogFragment : DialogFragment() {

    lateinit var dataPasser: OnDataPass

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_add_vehicle)
            .setPositiveButton("ok") { _, _ ->
                /*(requireParentFragment() as VehicleListFragment).addVehicleManual(
                    brandEditText.text.toString(),
                    modelEditText.text.toString(),
                    URLEditText.text.toString(),
                    SelfDrivingLevelEditText.text.toString()
                )*/
                passData(brandEditText.text.toString(),
                    modelEditText.text.toString(),
                    URLEditText.text.toString(),
                    SelfDrivingLevelEditText.text.toString()
                )
            }
            .show()
    }

    fun passData(brand: String, model: String, URL: String, SDL: String){
        dataPasser.onDataPass(brand, model, URL, SDL)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    interface OnDataPass {
        fun onDataPass(brand: String, model: String, URL: String, SDL: String)
    }

}