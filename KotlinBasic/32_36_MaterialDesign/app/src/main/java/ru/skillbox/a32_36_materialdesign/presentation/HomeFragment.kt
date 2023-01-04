package ru.skillbox.a32_36_materialdesign.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    val binding by viewBinding(FragmentHomeBinding::bind)

/*    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val botNavView = binding.bottomNavigationView
/*        botNavView.OnNavigationItemSelectedListener { item ->
            when(item.itemID) {
                else -> false
            }
        }*/
    }
}