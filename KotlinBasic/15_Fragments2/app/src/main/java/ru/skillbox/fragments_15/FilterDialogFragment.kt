package ru.skillbox.fragments_15

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class FilterDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val typesArticles = ArticleTypes.values().map { it.toString() }.toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle("Выберите тип статей")
            .setItems(typesArticles) { _, which ->
                (requireActivity() as TabsActivity).showTabsWithArticles(
                    ArticleTypes.values()[which]
                )
            }
            .create()
    }
}