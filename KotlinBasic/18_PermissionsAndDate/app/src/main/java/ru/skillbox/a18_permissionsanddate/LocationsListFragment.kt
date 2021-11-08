package ru.skillbox.a18_permissionsanddate

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_locations_list.*
import kotlinx.android.synthetic.main.fragment_locations_list.view.*
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.Instant
import java.util.*
import kotlin.random.Random

class LocationsListFragment : Fragment(R.layout.fragment_locations_list) {

    private var locations: List<Location> = listOf()
    private var locationAdapter: LocationAdapter? = null
    private var selectedLocationInstant: Instant? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
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
                    val geocoder = Geocoder(requireContext(), Locale.ENGLISH)
                    val listAddresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    val newLocation = Location(
                        id = Random.nextLong(),
                        //Yandex Static API, https://static-maps.yandex.ru/1.x/?{параметры URL}
                        //ll= Долгота и широта центра карты в градусах
                        //l= Перечень слоев, определяющих тип карты: map (схема), sat (спутник) и sat,skl (гибрид).
                        //z= Уровень масштабирования карты (0-17)
                        //"https://static-maps.yandex.ru/1.x/?ll=40.621400,56.218300&size=200,200&z=10&l=map"
                        address = listAddresses[0].getAddressLine(0),
                        picture = "https://static-maps.yandex.ru/1.x/?ll=${it.longitude},${it.latitude}&size=200,200&z=10&l=map",
                        accuracy = it.accuracy.toString(),
                        speed = it.speed.toString(),
                        wasIn = selectedLocationInstant ?: Instant.now()
                    )
                    locations = locations + listOf(newLocation)
                    locationAdapter?.submitList(locations)
                    //locationAdapter?.upda
                    locationAdapter?.notifyDataSetChanged()
                    selectedLocationInstant = null
                    toast("Запрос выполнен")
                }
                .addOnCanceledListener {
                    toast("Запрос локации был отменен")
                }
                .addOnFailureListener {
                    toast("Запрос локации завершился неудачно")
                }

        }
    }

    private fun showLocationInfo123123() {

        if (MainFragment().checkLocationPermission(requireContext())) {
            LocationServices.getFusedLocationProviderClient(requireContext())
                .lastLocation
                .addOnSuccessListener {
                    it?.let {
                        placeNameTextView.text = "qweqwe ${it.latitude}".trimIndent()
                    } ?: toast("Локация отсутсвует")
                }
                .addOnFailureListener {
                    toast("Запрос локации завершился неудачно")
                }
        }

    }

    private fun setTimeDate(position: Int) {
        toast("asdfasdasdasd")
    }
}