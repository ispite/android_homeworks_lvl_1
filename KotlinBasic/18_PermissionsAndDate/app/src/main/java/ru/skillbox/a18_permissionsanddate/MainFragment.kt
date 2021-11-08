package ru.skillbox.a18_permissionsanddate

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statementForNextFragment()
        getPermissionButton.setOnClickListener {
            requestLocationPermission()
        }
    }

    fun checkLocationPermission(requireContext: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun statementForNextFragment() {
        if (checkLocationPermission(requireContext())) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, LocationsListFragment())
                .commit()
        } else {
            statePermissionTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
            statePermissionTextView.text = "Для отображения списка локаций необходимо разрешение"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            statementForNextFragment()
        } else {
            toastWarning()
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun toastWarning() {
        if (!checkLocationPermission(requireContext())) {
            Toast.makeText(
                requireContext(),
                "Необходимо разрешение местоположения для работы приложения",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 5465
    }
}