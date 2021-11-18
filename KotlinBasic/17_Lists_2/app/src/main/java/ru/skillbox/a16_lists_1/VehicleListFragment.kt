package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import ru.skillbox.a16_lists_1.adapters.VehicleAdapter
import kotlin.collections.ArrayList
import kotlin.random.Random
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.a16_lists_1.adapters.VehicleAdapter.Companion.newInstance


class VehicleListFragment : Fragment(R.layout.fragment_vehicle_list),
    NewVehicleDialogFragment.NewVehicleDialogListener {

    private var vehicles = generateVehicles(2)
    private var vehicleAdapter by AutoClearedValue<VehicleAdapter>(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            initListVehicles()
            addFAB.setOnClickListener { addVehicle() }
            addFABManual.setOnClickListener {
                NewVehicleDialogFragment()
                    .show(childFragmentManager, "DIALOG")
            }
            Log.d("State", "Create list")
            vehicleAdapter.items = vehicles
        } else {
            Log.d("State", "start restoring")
            val listState: Parcelable? = savedInstanceState.getParcelable(KEY_LISTSTATE)
            if (listState != null) {
                initListVehicles()
                addFAB.setOnClickListener { addVehicle() }
                addFABManual.setOnClickListener {
                    NewVehicleDialogFragment()
                        .show(childFragmentManager, "DIALOG")
                }
                Log.d("State", "listState != null")
                vehicles =
                    savedInstanceState.getParcelableArrayList<Vehicle>(KEY_LISTVEHICLE) as ArrayList<Vehicle>
                vehicleAdapter.items = vehicles
                vehicleList.layoutManager?.onRestoreInstanceState(listState)
            }
        }
    }

    private fun generateVehicles(count: Int): List<Vehicle> {
        val brands = listOf(
            "Volkswagen",
            "Volvo",
            "BMW",
            "Tesla",
            "Audi",
            "Lamborghini"
        )

        val models = listOf(
            "Passat b6",
            "S60",
            "3 series",
            "Model 3",
            "A8",
            "Aventador"
        )

        val images = listOf(
            "https://naked-science.ru/wp-content/uploads/2020/07/record-skyline-building-skyscraper-cityscape-vehicle-836758-pxhere.com_.jpg", //"https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg"
            "https://life-globe.com/image/cache/catalog/italia/bologna/dve-bashni-bologni/le-due-torri-bologna-600x917.jpg", //"https://autoiwc.ru/images/volvo/volvo-s60.jpg"
            "https://img.tourister.ru/files/1/5/8/8/0/7/5/8/original.jpg", //"https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg"
            "https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg",
            "https://s0.rbk.ru/v6_top_pics/resized/1440xH/media/img/3/64/754788601082643.jpeg",
            "https://topgearrussia.ru/data/topgear/upload/2012-08/23/image-45f06bb6.jpg"
        )

        return (0..count).map {
            val id = it.toLong()
            val brand = brands.random()
            val model = models.random()
            val image = images.random()
            val selfDrivingLevel = Random.nextInt(1, 4)
            val isSelfDrivingCar = Random.nextBoolean()

            if (isSelfDrivingCar) {
                Vehicle.SelfDrivingCar(
                    id = id,
                    brand = brand,
                    model = model,
                    image = image,
                    selfDrivingLevel = selfDrivingLevel
                )
            } else {
                Vehicle.Car(
                    id = id,
                    brand = brand,
                    model = model,
                    image = image
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("State", "start saving")
        val listState = vehicleList.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_LISTSTATE, listState)
        Log.d("State", "PUT PARCEL")
        outState.putParcelableArrayList(KEY_LISTVEHICLE, vehicles as ArrayList<out Parcelable>)
        Log.d("State", "PARCEL PUTED")
    }

    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    private fun initListVehicles() {
        val args = requireArguments()
//        vehicleAdapter = VehicleAdapter({ position -> deleteVehicle(position) }, args.getInt(KEY_TABNUMBER))
        vehicleAdapter =
            VehicleAdapter.newInstance({ position ->
                deleteVehicle(position)
            }, args.getInt(KEY_TABNUMBER))
        with(vehicleList) {
            adapter = vehicleAdapter
            when (args.getInt(KEY_TABNUMBER)) {
                0 -> {
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
                }
                1 -> {
                    //возможный вариант решения от alexdomih канал:Профессия Android-разработчик 2020 (Skillbox)
                    layoutManager = GridLayoutManager(requireContext(), 3)/*.apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (position % 4 == 1 || position % 4 == 2) 2 else 1
                            }
                        }
                    }*/
                    scrollListener = object :
                        EndlessRecyclerViewScrollListener(layoutManager as GridLayoutManager) {
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
                }
                2 -> {
                    addItemDecoration(ItemOffsetDecoration(requireContext()))
                    layoutManager =
                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

                    scrollListener = object :
                        EndlessRecyclerViewScrollListener(layoutManager as StaggeredGridLayoutManager) {
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
                }
            }
            setHasFixedSize(true)
            itemAnimator = FlipInTopXAnimator()
        }
    }

    fun loadNextDataFromApi(offset: Int) {
        if (vehicleAdapter.items.count() < 10) {
            vehicleAdapter.items = vehicleAdapter.items + generateVehicles(offset)
        }
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }


    private fun deleteVehicle(position: Int) {
        vehicles =
            vehicles.filterIndexed { index, vehicle -> index != position } as ArrayList<Vehicle>
        vehicleAdapter.items = vehicles
        if (vehicles.isEmpty()) {
            emptyListNotification.visibility = View.VISIBLE
        }
    }

    private fun addVehicle() {
        val newVehicle = vehicles.random().let {
            when (it) {
                is Vehicle.Car -> it.copy(id = Random.nextLong())
                is Vehicle.SelfDrivingCar -> it.copy(id = Random.nextLong())
            }
        }
        vehicles = (listOf(newVehicle) + vehicles) as ArrayList<Vehicle>
        vehicleAdapter.items = vehicles
        vehicleList.scrollToPosition(0)
    }

    private fun addVehicleManual(
        id: String?,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        val newVehicle = when (selfDrivingLevel?.toIntOrNull()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                id!!.toLong(),
                brand = brand!!,
                model = model!!,
                image = image!!,
                selfDrivingLevel!!.toInt()
            )
            else -> Vehicle.Car(
                id = id!!.toLong(),
                brand = brand!!,
                model = model!!,
                image = image!!
            )
        }
        vehicles = (listOf(newVehicle) + vehicles) as ArrayList<Vehicle>
        vehicleAdapter.items = vehicles
        vehicleList.scrollToPosition(0)
        if (vehicles.isNotEmpty()) {
            emptyListNotification.visibility = View.GONE
        }
    }

    override fun passArguments(
        id: String?,
        brand: String?,
        model: String?,
        URL: String?,
        SDL: String?
    ) {
        Toast.makeText(context, brand, Toast.LENGTH_SHORT).show()
        addVehicleManual(id, brand, model, URL, SDL)
    }

    companion object {
        private const val KEY_LISTSTATE = "LISTSTATE"
        private const val KEY_LISTVEHICLE = "LISTVEHICLE"
        private const val KEY_TABNUMBER = "TABNUMBER"

        fun newInstance(tabNumber: Int): VehicleListFragment {
            return VehicleListFragment().withArguments {
                putInt(KEY_TABNUMBER, tabNumber)
            }
        }
    }


}




























