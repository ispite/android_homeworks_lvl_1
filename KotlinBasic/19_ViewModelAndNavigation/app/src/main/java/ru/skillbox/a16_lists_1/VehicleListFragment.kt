package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import ru.skillbox.a16_lists_1.adapters.VehicleAdapter

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
        observeViewModelState()
    }


    private fun initListVehicles() {
        vehicleAdapter = VehicleAdapter(
            { id, vehiclePhoto, trueID ->
                val action =
                    VehicleListFragmentDirections.actionVehicleListFragmentToDetailsFragment(
                        id + 1,
                        vehiclePhoto,
                        trueID
                    )
                findNavController().navigate(action)
            }, { id -> deleteVehicle(id.toInt()); true }
        )
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
        if (vehicleListViewModel.vehicles.value!!.count() < 0) {
            vehicleListViewModel.generateVehicles(offset)
        }
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    private fun addVehicle() {
        vehicleAdapter.setEmptyList()
        vehicleListViewModel.addVehicle()
        vehicleList.scrollToPosition(0)
    }

    private fun deleteVehicle(position: Int) {
        vehicleListViewModel.deleteVehicle(position)
    }

    private fun observeViewModelState() {
        vehicleListViewModel.vehicles
            .observe(viewLifecycleOwner) { newVehicles ->
                vehicleAdapter.items = newVehicles
                vehicleList.scrollToPosition(0)
/*                if (vehicleAdapter.items.isEmpty()) {
                    Log.d("DELETE_ITEM", " items are empty")
                    emptyListNotification.visibility = View.VISIBLE
                } else emptyListNotification.visibility = View.GONE*/
                if (vehicleListViewModel.vehicles.value!!.isEmpty()) {
                    Log.d("DELETE_ITEM", " items are empty")
                    emptyListNotification.visibility = View.VISIBLE
                } else emptyListNotification.visibility = View.GONE
            }
        vehicleListViewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Элемент добавлен", Toast.LENGTH_SHORT).show()
        }
        vehicleListViewModel.showDeleteToast.observe(viewLifecycleOwner) { positionFromViewModel ->
            Log.d("DELETE_ITEM", "position = ${positionFromViewModel + 1}")
            Toast.makeText(
                requireContext(),
                "Элемент №${positionFromViewModel + 1} удалён",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun addVehicleManual(
        id: Long,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        vehicleListViewModel.addVehicleManual(id, brand, model, image, selfDrivingLevel)

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
        addVehicleManual(id, brand, model, URL, SDL)
    }

}