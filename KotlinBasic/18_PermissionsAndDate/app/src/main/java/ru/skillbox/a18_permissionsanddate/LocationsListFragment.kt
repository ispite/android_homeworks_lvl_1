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
import android.os.Parcelable
import com.google.android.gms.location.*
import com.google.android.gms.common.ConnectionResult

import com.google.android.gms.common.GoogleApiAvailability
import androidx.core.content.pm.PackageInfoCompat
import com.google.android.gms.common.GooglePlayServicesUtil
import kotlin.collections.ArrayList
import android.app.Activity


class LocationsListFragment : Fragment(R.layout.fragment_locations_list) {

    private var locations: List<Location> = listOf()
    private var locationAdapter: LocationAdapter? = null
    private var selectedLocationInstant: Instant? = null

    fun isGooglePlayServicesAvailable123123(activity: Activity?): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show()
            }
            return false
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initList()
            when (isGooglePlayServicesAvailable(requireContext())) {
                ConnectionResult.SERVICE_MISSING -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("У Вас отсутствуют Google Play Services!")
                        .setCancelable(false)
                        .show()
                }
                ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED -> {
                    val googleApiAvailability = GoogleApiAvailability.getInstance()
                    val status =
                        googleApiAvailability.isGooglePlayServicesAvailable(requireContext())
                    googleApiAvailability.getErrorDialog(requireParentFragment(), status, 2404)
                        .show()
                    toast("Надо обновиться")
                }
            }

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            getLocationUpdates()
            getLocationButton.setOnClickListener {

                showLocationInfo()
            }

        } else {
            val liststate: Parcelable? = savedInstanceState.getParcelable(KEY_LISTSTATE)
            if (liststate != null) {
                initList()

                locations =
                    savedInstanceState.getParcelableArrayList<Location>(KEY_LISTLOCATIONS) as ArrayList<Location>
                locationAdapter?.submitList(locations)
                locationAdapter?.notifyDataSetChanged()

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                getLocationUpdates()
                getLocationButton.setOnClickListener {
                    showLocationInfo()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val listState = locationsListRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_LISTSTATE, listState)
        outState.putParcelableArrayList(KEY_LISTLOCATIONS, locations as ArrayList<out Parcelable>)
    }

    /*        val v2: Int = requireContext().packageManager.getPackageInfo(
            GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
            0
        ).versionCode*/

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
                    if (locations.isNotEmpty()) {
                        val lastLocationLatitude = locations.last().latitude
                        val lastLocationLongitude = locations.last().longitude
                        if (lastLocationLatitude != location.latitude || lastLocationLongitude != location.longitude) {
                            setNewLocation(location)
                        }
                    } else setNewLocation(location)
                    //setNewLocation(location)
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
            latitude = location.latitude,
            longitude = location.longitude,
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

    private fun isGooglePlayServicesAvailable(context: Context): Int {
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


    companion object {
        private const val KEY_LISTSTATE = "LISTSTATE"
        private const val KEY_LISTLOCATIONS = "LISTLOCATIONS"
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