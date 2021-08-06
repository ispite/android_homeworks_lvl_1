package ru.skillbox.fragments_15

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class FilterDialogFragment : DialogFragment() {

    /*companion object {
        private const val KEY_ARTICLE = "KEY_ARTICLE"
        fun newInstance(number: Int): FilterDialogFragment{
            return FilterDialogFragment().withArguments {
                putInt(KEY_ARTICLE, number)
            }
        }
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        /*return AlertDialog.Builder(requireActivity())
            .setTitle("Delete item")
            .setMessage("Вы Шура?")
            .setPositiveButton("YES", { _, _ -> toast("clicked YES")})
            //.setPositiveButton("YES", { _, _ -> listener.onConfirm() }) //26:35
            //setPositiveButton(requireArguments().getString() { _, _ -> }) //27:14
            .setNegativeButton("NO", { _, _ -> toast("clicked NO")})
            .setNeutralButton("NOthing", { _, _ -> toast("clicked NOthing")})
            .create()*/

        val typesArticles = arrayOf("Everything", "Business", "Sport", "Health")
        return AlertDialog.Builder(requireActivity())
            .setTitle("Выберите тип статей")
            //.setItems(typesArticles) {_, which -> toast("Выбран = ${typesArticles[which]}")} //13:39
            .setItems(typesArticles) { _, which -> //TabsActivity.doAnything()//НЕ ЗНАЮ ТАК ВООБЩЕ МОЖНО
                toast("Выбран = ${typesArticles[which]}")
            }
            .create()
    }

    private fun toast(text: String) {
        Toast.makeText(this.activity, text, Toast.LENGTH_LONG).show()
    }
}