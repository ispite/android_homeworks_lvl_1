package ru.skillbox.constraintlayoutandtoolbars_11_2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_coordinator.*

class CoordinatorActivity : AppCompatActivity() {

    private val users = listOf(
        "User1",
        "User2",
        "User3",
        "Unknown"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        toolbar.setTitle(R.string.app_name_activity)
        toolbar.title = "Amazing Homework 11.2"
        initToolbar()

    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        toolbar.setNavigationOnClickListener {
            toast("pressed")
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_1 -> {
                    toast("action 1 is clicked")
                    true
                }
                R.id.action_2 -> {
                    toast("action 2 is clicked")
                    true
                }
                R.id.action_3 -> {
                    toast("action 3 is clicked")
                    true
                }
                else -> false
            }
        }
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                ExpandTextView.text = "search expanded"
                ExpandTextView.visibility = View.VISIBLE
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                ExpandTextView.text = "search collapsed"
                Handler(Looper.getMainLooper()).postDelayed({
                    ExpandTextView.visibility = View.GONE
                    SearchResultTextView.visibility = View.GONE
                }, 2000)
                return true
            }
        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    SearchResultTextView.visibility = View.VISIBLE
                    users.filter { it.contains(other = newText ?: "", ignoreCase = true) }
                        .joinToString()
                        .let {
                            SearchResultTextView.text = it
                        }
                    return true
                }

            })
    }

/*    fun AppBarLayout.showElevation(isElevation: Boolean) {
        SdkUtils.runOnLollipop {
            elevation = if (isElevation) 8F else 0F
        }
    }*/
}