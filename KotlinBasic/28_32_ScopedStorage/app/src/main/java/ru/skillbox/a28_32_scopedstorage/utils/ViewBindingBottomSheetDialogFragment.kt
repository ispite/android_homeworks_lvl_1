package ru.skillbox.a28_32_scopedstorage.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ViewBindingBottomSheetDialogFragment<T : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater
    ) -> T
) : BottomSheetDialogFragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding.invoke(LayoutInflater.from(requireContext()))
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}