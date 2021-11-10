package ru.skillbox.a18_permissionsanddate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_locations_list.*
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.common.ConnectionResult

import com.google.android.gms.common.GoogleApiAvailability
import androidx.core.content.pm.PackageInfoCompat
import com.google.android.gms.common.GooglePlayServicesUtil


class LocationsListFragment : Fragment(R.layout.fragment_locations_list) {

    private var locations: List<Location> = listOf()
    private var locationAdapter: LocationAdapter? = null
    private var selectedLocationInstant: Instant? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()

        when (isGooglePlayServicesAvailable(requireContext())) {
            1 -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("У Вас отсутствуют Google Play Services!")
                    .setCancelable(false)
                    .show()
            }
            2 -> {
                GooglePlayServicesUtil.getErrorDialog(
                    isGooglePlayServicesAvailable(requireContext()),
                    requireActivity(),
                    0
                ).show();

                //val googleAPI = GoogleApiAvailability.getInstance()
                //googleAPI.getErrorDialog(this, isGooglePlayServicesAvailable(requireContext()), PLAY_SERVICES_RESOLUTION_REQUEST).show()
                //googleAPI.getErrorDialog(this, isGooglePlayServicesAvailable(requireContext()), PLAY_SERVICES_RESOLUTION_REQUEST).show()
                toast("Надо обновиться")
            }
        }

/*        val v2: Int = requireContext().packageManager.getPackageInfo(
            GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
            0
        ).versionCode*/

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLocationUpdates()
        getLocationButton.setOnClickListener {
            //showLocationInfo()
            //locationNotificationTextView.visibility = View.GONE
            //initList()
            showLocationInfo()
        }
    }

    private fun initList() = with(locationsListRecyclerView) {
        //locationNotificationTextView.visibility = View.GONE
        //this.visibility = View.VISIBLE
        locationAdapter = LocationAdapter { position -> setTimeDate(position) }
        adapter = locationAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        //locationAdapter?.submitList(locations)
    }

    private fun showLocationInfo() {
        if (MainFragment().checkLocationPermission(requireContext())) {
            LocationServices.getFusedLocationProviderClient(requireContext())
                .lastLocation
                .addOnSuccessListener {
                    setNewLocation(it)
                }
                .addOnCanceledListener {
                    toast("Запрос локации был отменен")
                }
                .addOnFailureListener {
                    toast("Запрос локации завершился неудачно")
                }
        }
    }

    private fun setTimeDate(position: Int) {
        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val zonedDateTime =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneId.systemDefault())

                        toast("Выбрано время: $zonedDateTime")

                        editTimeDateField(position, zonedDateTime.toInstant())

                        selectedLocationInstant = zonedDateTime.toInstant()
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                )
                    .show()
            },
            currentDateTime.year,
            currentDateTime.month.value - 1,
            currentDateTime.dayOfMonth
        )
            .show()
        //toast("asdfasdasdasd")
    }

    private fun editTimeDateField(position: Int, timeDate: Instant) {
        locations[position].wasIn = timeDate
        locationAdapter?.submitList(locations)
        locationAdapter?.notifyDataSetChanged()
    }

    // declare a global variable FusedLocationProviderClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // in onCreate() initialize FusedLocationProviderClient
    //fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

    // globally declare LocationRequest
    private lateinit var locationRequest: LocationRequest

    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback

    /**
     * call this method in onCreate
     * onLocationResult call when location is changed
     */


    private fun getLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                getLocationUpdates()
                if (locationResult.locations.isNotEmpty()) {
                    // get latest location
                    val location =
                        locationResult.lastLocation
                    // use your location object
                    // get latitude , longitude and other info from this
                    setNewLocation(location)
                }
            }
        }
    }

    //start location updates
    private fun startLocationUpdates() {
        if (MainFragment().checkLocationPermission(requireContext())) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
            )
        }
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun setNewLocation(location: android.location.Location) {
        val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
        val listAddresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val newLocation = Location(
            id = Random.nextLong(),
            //Yandex Static API, https://static-maps.yandex.ru/1.x/?{параметры URL}
            //ll= Долгота и широта центра карты в градусах
            //l= Перечень слоев, определяющих тип карты: map (схема), sat (спутник) и sat,skl (гибрид).
            //z= Уровень масштабирования карты (0-17)
            //"https://static-maps.yandex.ru/1.x/?ll=40.621400,56.218300&size=200,200&z=10&l=map"
            address = listAddresses[0].getAddressLine(0),
            picture = "https://static-maps.yandex.ru/1.x/?ll=${location.longitude},${location.latitude}&size=150,150&z=10&l=map",
            accuracy = location.accuracy.toString(),
            speed = location.speed.toString(),
            wasIn = selectedLocationInstant ?: Instant.now()
        )
        locations = locations + listOf(newLocation)
        locationAdapter?.submitList(locations)
        //locationAdapter?.upda
        locationAdapter?.notifyDataSetChanged()
        selectedLocationInstant = null
        //toast("Запрос выполнен")
    }

    fun isGooglePlayServicesAvailable(context: Context): Int {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        //return resultCode == ConnectionResult.SUCCESS
        return googleApiAvailability.isGooglePlayServicesAvailable(context)
    }

    private fun checkVersionGooglePlayServices(): Long {
        return PackageInfoCompat.getLongVersionCode(
            requireContext().packageManager.getPackageInfo(
                GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
                0
            )
        )
    }
}

/*        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)

        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                //prompt the dialog to update google play
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show()
            }
        } else {
            //google play up to date
        }*/


/*val googleApiAvailability = GoogleApiAvailability.getInstance()
val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
val isGooglePlayServicesAvailable= resultCode == ConnectionResult.SUCCESS*/

//isGooglePlayServicesAvailable(requireContext())
/*        if (isGooglePlayServicesAvailable(requireContext())) {
            if (checkVersionGooglePlayServices() > 1) {
                toast("Всё хорошо ${checkVersionGooglePlayServices()}")
            } else {
                toast("Всё плохо")
            }
            AlertDialog.Builder(requireContext())
                .setTitle("У Вас отсутствуют Google Play Services!")
                .setCancelable(false)
                .show()
        }*/