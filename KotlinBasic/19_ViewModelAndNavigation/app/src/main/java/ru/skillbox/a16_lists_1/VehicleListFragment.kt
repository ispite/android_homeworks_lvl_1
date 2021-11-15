package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import ru.skillbox.a16_lists_1.adapters.VehicleAdapter
//import java.util.ArrayList
import kotlin.collections.ArrayList
import kotlin.random.Random

class VehicleListFragment : Fragment(R.layout.fragment_vehicle_list),
    NewVehicleDialogFragment.NewVehicleDialogListener {
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private val vehicleListViewModel: VehicleListViewModel by viewModels()

    private var vehicleAdapter by AutoClearedValue<VehicleAdapter>(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListVehicles()
        addFAB.setOnClickListener { addVehicle() }
        addFABManual.setOnClickListener {
            NewVehicleDialogFragment()
                .show(childFragmentManager, "DIALOG")
        }
        updateVehicleList()
    }

    private fun initListVehicles() {
        vehicleAdapter = VehicleAdapter { position -> deleteVehicle(position) }
        with(vehicleList) {
            adapter = vehicleAdapter
            layoutManager = LinearLayoutManager(requireContext())

            //БЛОК кода из примера https://guides.codepath.com/android/endless-scrolling-with-adapterviews-and-recyclerview#implementing-with-recyclerview
            /////
            scrollListener = object :
                EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    loadNextDataFromApi(page)
                }
            }
            // Adds the scroll listener to RecyclerView
            vehicleList.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
            /////

            setHasFixedSize(true)
            itemAnimator = FlipInTopXAnimator()
        }
    }

    fun loadNextDataFromApi(offset: Int) {
        if (vehicleAdapter.items.count() < 5) {
            vehicleAdapter.items = vehicleAdapter.items + vehicleListViewModel.generateVehicles(offset)
        }
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    private fun addVehicle() {
        vehicleListViewModel.addVehicle()
        updateVehicleList()
        //vehicleList.scrollToPosition(0)
    }

    private fun deleteVehicle(position: Int) {
        vehicleListViewModel.deleteVehicle(position)
        updateVehicleList()
        if (vehicleAdapter.items.isEmpty()) {
            emptyListNotification.visibility = View.VISIBLE
        }
    }

    private fun updateVehicleList() {
        vehicleAdapter.items = vehicleListViewModel.getVehicleList()
        vehicleList.scrollToPosition(0)
    }

    private fun addVehicleManual(
        id: Long,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        /*val newVehicle = when (selfDrivingLevel?.toIntOrNull()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                id = id,
                brand = brand!!,
                model = model!!,
                image = image!!,
                selfDrivingLevel!!.toInt()
            )
            else -> Vehicle.Car(id = id, brand = brand!!, model = model!!, image = image!!)
        }*/

        vehicleListViewModel.addVehicleManual(id, brand, model, image, selfDrivingLevel)

        //vehicleAdapter.items = (listOf(newVehicle) + vehicleAdapter.items) as ArrayList<Vehicle>
        updateVehicleList()
        //vehicleList.scrollToPosition(0)
        if (vehicleAdapter.items.isNotEmpty()) {
            emptyListNotification.visibility = View.GONE
        }
    }

    override fun passArguments(
        id: Long,
        brand: String?,
        model: String?,
        URL: String?,
        SDL: String?
    ) {
        Toast.makeText(context, brand, Toast.LENGTH_SHORT).show()
        addVehicleManual(id, brand, model, URL, SDL)
    }

}