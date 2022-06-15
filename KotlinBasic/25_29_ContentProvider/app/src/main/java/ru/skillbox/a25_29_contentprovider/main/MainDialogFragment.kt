package ru.skillbox.a25_29_contentprovider.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import ru.skillbox.a25_29_contentprovider.R

class MainDialogFragment : DialogFragment() {

    private var listener: MainDialogListener? = null

/*    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }*/

//    private val viewModel by viewModels<MainViewModel>()
    private val viewModel by viewModels<MainDialogViewModel>()
//    val args: MainDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view = inflater.inflate(R.layout.dialog_download_file, null)

        val inputURL = view.findViewById<TextInputEditText>(R.id.inputIdEditText)
        val navController = findNavController()

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                val str = inputURL!!.text.toString()
                listener!!.passDownloadLink(
                    str
                )

/*                viewModel.storeLink(str) {
                        val arg = MainDialogFragmentArgs(it).toBundle()
                        Log.d("MainDialogFragment", "onCreateDialog: ")

                    }*/
            }
            .create()
    }

/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            parentFragment as MainDialogListener
//            requireParentFragment() as MainDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement MainDialogListener")
        }
    }

    interface MainDialogListener {
        fun passDownloadLink(link: String)
    }
}