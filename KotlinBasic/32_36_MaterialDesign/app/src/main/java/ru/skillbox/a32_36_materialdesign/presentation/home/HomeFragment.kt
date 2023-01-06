package ru.skillbox.a32_36_materialdesign.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a32_36_materialdesign.R
import ru.skillbox.a32_36_materialdesign.data.Product
import ru.skillbox.a32_36_materialdesign.databinding.FragmentHomeBinding
import ru.skillbox.a32_36_materialdesign.utils.autoCleared
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private var productAdapter: ProductAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        productAdapter.submitList(generateProductList(100))
    }

    private fun initList() {
        productAdapter = ProductAdapter { transitionToProductFragment(it) }
        with(binding.itemListRecyclerView) {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun generateProductList(count: Int): List<Product> {
        val titles = listOf(
            "Смартфон",
            "Мультипекарь",
            "Корм сухой",
            "Увлажнитель воздуха",
            "Кофе в зернах",
            "Машина посудомоечная"
        )

        val descriptions = listOf(
            "Телефон (со встроенной батареей)",
            "Это универсальное устройство, заменяющее более 40 приборов* для выпечки дома по рецептам разных кухонь мира",
            "Беззерновой корм класса холистик \"Primordial\" создан для стерилизованных кошек",
            "Новая технология верхнего залива \"WATERWAY PRO\" – для залива воды достаточно снять крышку.",
            "Кофе торговой марки Carraro - настоящий итальянский напиток",
            "Функционал посудомоечной машины: Использование средств 3-в-1, Отсрочка запуска, Половинная загрузка"
        )

        val images = listOf(
            "https://cdn1.ozone.ru/s3/multimedia-f/wc1000/6487666563.jpg",
            "https://cdn1.ozone.ru/s3/multimedia-4/wc1000/6333044932.jpg",
            "https://cdn1.ozone.ru/s3/multimedia-c/wc1000/6471805716.jpg",
            "https://cdn1.ozone.ru/s3/multimedia-m/wc1000/6338680498.jpg",
            "https://cdn1.ozone.ru/s3/multimedia-8/wc1000/6355869332.jpg",
            "https://cdn1.ozone.ru/s3/multimedia-d/wc1000/6223322341.jpg"
        )

        return (0..count).map {
            val id = it.toLong()
            val title = titles.random()
            val description = descriptions.random()
            val image = images.random()
            Product(
                id = id,
                title = title,
                description = description,
                image = image,
            )
        }
    }

    private fun transitionToProductFragment(product: Product) {
        Timber.d("id=$id")
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToProductFragment(product)
        )
    }
}