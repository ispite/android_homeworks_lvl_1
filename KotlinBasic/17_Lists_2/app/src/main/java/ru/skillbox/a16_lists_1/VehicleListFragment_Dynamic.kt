package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
//import java.util.ArrayList
import kotlin.collections.ArrayList

class VehicleListFragment_Dynamic : Fragment(R.layout.fragment_vehicle_list),
    NewVehicleDialogFragment.NewVehicleDialogListener {

    private var vehicles = arrayListOf(
        Vehicle.Car(
            brand = "Volkswagen",
            model = "Passat b6",
            image = "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg"
        ),
        Vehicle.SelfDrivingCar(
            brand = "Volvo",
            model = "S60",
            image = "https://autoiwc.ru/images/volvo/volvo-s60.jpg",
            selfDrivingLevel = 4
        ),
        Vehicle.Car(
            brand = "BMW",
            model = "3 series",
            image = "https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg"
        ),
        Vehicle.SelfDrivingCar(
            brand = "Tesla",
            model = "Model 3",
            image = "https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg",
            selfDrivingLevel = 4
        )
    )
    //    БЫЛО
    //    private var vehicleAdapter: VehicleAdapter? = null

    //    СТАЛО
    private var vehicleAdapter by AutoClearedValue<VehicleAdapter_Dynamic>(this)
    //vehicleAdapter = VehicleAdapter

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val args = requireArguments()
        view?.apply {

            vehicleAdapter = VehicleAdapter { position -> deleteVehicle(position) }
            with(vehicleList) {
                adapter = vehicleAdapter
                args.getInt(KEY_TABNUMBER)
                when (args.getInt(KEY_TABNUMBER)) {
                    0 -> layoutManager = LinearLayoutManager(requireContext())
                    1 -> layoutManager = GridLayoutManager(requireContext(),3)
                    2 -> layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                }
                //layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }



        }
        return view
    }*/

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
            vehicleAdapter.updateVehicles(vehicles)
            vehicleAdapter.notifyItemRangeInserted(0, vehicles.size)
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
                vehicleAdapter.updateVehicles(vehicles)
                vehicleAdapter.notifyItemRangeInserted(0, vehicles.size)
                vehicleList.layoutManager?.onRestoreInstanceState(listState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("State", "start saving")
        val listState = vehicleList.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_LISTSTATE, listState)
//        outState.putParcelable(KEY_LISTVEHICLE, vehicles)
//        outState.putParcelableArray(KEY_LISTVEHICLE, vehicles)
        Log.d("State", "PUT PARCEL")
        outState.putParcelableArrayList(KEY_LISTVEHICLE, vehicles as ArrayList<out Parcelable>)
        Log.d("State", "PARCEL PUTED")
    }

    private fun initListVehicles() {
        val args = requireArguments()
        vehicleAdapter = VehicleAdapter_Dynamic { position -> deleteVehicle(position) }
        with(vehicleList) {
            adapter = vehicleAdapter

            when (args.getInt(KEY_TABNUMBER)) {
                0 -> layoutManager = LinearLayoutManager(requireContext())
                1 -> layoutManager = GridLayoutManager(requireContext(),3)
                2 -> {
/*                    val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

                    addItemDecoration(dividerItemDecoration)
                    addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL))
                    addItemDecoration(ItemOffsetDecoration(requireContext()))*/
                    layoutManager =
                        StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                }
            }

//            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteVehicle(position: Int) {
        vehicles =
            vehicles.filterIndexed { index, vehicle -> index != position } as ArrayList<Vehicle>
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemRemoved(position)
        if (vehicles.isEmpty()) {
            emptyListNotification.visibility = View.VISIBLE
        }
    }

    private fun addVehicle() {
        val newVehicle = vehicles.random()
        //vehicles = listOf(newVehicle) + vehicles
        vehicles = (listOf(newVehicle) + vehicles) as ArrayList<Vehicle>
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)
    }

    private fun addVehicleManual(
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        val newVehicle = when (selfDrivingLevel?.toIntOrNull()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                brand = brand!!,
                model = model!!,
                image = image!!,
                selfDrivingLevel!!.toInt()
            )
            else -> Vehicle.Car(brand = brand!!, model = model!!, image = image!!)
        }
        vehicles = (listOf(newVehicle) + vehicles) as ArrayList<Vehicle>
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)
        if (vehicles.isNotEmpty()) {
            emptyListNotification.visibility = View.GONE
        }
    }

    override fun passArguments(brand: String?, model: String?, URL: String?, SDL: String?) {
        Toast.makeText(context, brand, Toast.LENGTH_SHORT).show()
        addVehicleManual(brand, model, URL, SDL)
    }

    companion object {
        private const val KEY_LISTSTATE = "LISTSTATE"
        private const val KEY_LISTVEHICLE = "LISTVEHICLE"

        private const val KEY_TABNUMBER = "TABNUMBER"

        fun newInstance(tabNumber: Int): VehicleListFragment_Dynamic {
            return VehicleListFragment_Dynamic().withArguments {
                putInt(KEY_TABNUMBER, tabNumber)
            }
        }
    }
}