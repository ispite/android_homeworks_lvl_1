package ru.skillbox.fragments_15

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class FilterDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //не получается сразу привести к массиву со строками
        val typesArticles5 = ArticleTypes.values().forEach { element -> listOf<String>(element.toString()) }

        //Очевидно не оптимальный код!
        val typesArticles1 = ArticleTypes.values()
        val typesArticles2 : MutableList<String> = mutableListOf<String>()
        for (i in typesArticles1.indices) {
            typesArticles2.add(typesArticles1[i].toString())
        }
        val typesArticles3 = typesArticles2.toTypedArray()
        /////////////////////////////////////////////////

        return AlertDialog.Builder(requireContext())
            .setTitle("Выберите тип статей")
            .setItems(typesArticles3) { _, which -> (requireActivity() as TabsActivity).showTabsWithArticles(
                ArticleTypes.values()[which]
            )
            }
            .create()
    }

    private fun toast(text: String) {
        Toast.makeText(this.activity, text, Toast.LENGTH_LONG).show()
    }
}