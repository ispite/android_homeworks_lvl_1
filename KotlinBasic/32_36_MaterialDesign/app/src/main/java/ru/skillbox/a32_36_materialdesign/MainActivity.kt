package ru.skillbox.a32_36_materialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.skillbox.a32_36_materialdesign.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navView = binding.activityBottomNavigationView
        // Используй эту строку, если в разметке используется <fragment />
//        val navController = findNavController(R.id.fragmentContainerView)

        // Используй эти строки, если в разметке используется <FragmentContainerView />
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.listFragment, R.id.favoriteFragment, R.id.profileFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        showSnackBar()
    }

    private fun showSnackBar() {
        val constraintLayout = binding.root
//        val constraintLayout = binding.mainActivityConstraintLayout
        val anchor = binding.activityBottomNavigationView
        Snackbar.make(
            constraintLayout,
            "Соединение с сервером отсутствует, показаны сохранённые объекты",
            Snackbar.LENGTH_LONG
        ).setAction("Повторить") {
                Snackbar.make(constraintLayout, "Список обновлён", Snackbar.LENGTH_LONG).show()
            }.setAnchorView(anchor).show()
    }
}