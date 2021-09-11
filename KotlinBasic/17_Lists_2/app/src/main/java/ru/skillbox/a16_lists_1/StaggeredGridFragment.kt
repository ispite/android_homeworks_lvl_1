package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_linear.imageList
import kotlinx.android.synthetic.main.fragment_staggered_grid.*

class StaggeredGridFragment:Fragment(R.layout.fragment_staggered_grid) {

    private var vehicleImages = arrayListOf(
        "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg",
        "https://autoiwc.ru/images/volvo/volvo-s60.jpg",
        "https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg",
        "https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg",
        "https://topgearrussia.ru/data/topgear/upload/2012-08/23/image-45f06bb6.jpg",
        "https://s0.rbk.ru/v6_top_pics/resized/1440xH/media/img/3/64/754788601082643.jpeg"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
    }

    private fun initList() = with(imageListStaggered) {
        adapter = ImageAdapter().apply {
            setImages(vehicleImages + vehicleImages + vehicleImages + vehicleImages + vehicleImages)
            setListItem(R.layout.item_car_image_2)
        }
        setHasFixedSize(true)
        //addItemDecoration(ItemOffsetDecoration(requireContext()))
        layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }
}