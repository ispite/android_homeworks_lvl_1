package ru.skillbox.fragments_15

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class FilterDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val typesArticles5 = ArticleTypes.values().map { it.toString()}.toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle("Выберите тип статей")
            .setItems(typesArticles5) { _, which -> (requireActivity() as TabsActivity).showTabsWithArticles(
                ArticleTypes.values()[which]
            )
            }
            .create()
    }

    private fun toast(text: String) {
        Toast.makeText(this.activity, text, Toast.LENGTH_LONG).show()
    }
}