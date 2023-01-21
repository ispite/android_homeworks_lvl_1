package ru.skillbox.dependency_injection.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.skillbox.dependency_injection.presentation.images.list.ImagesFragment
import ru.skillbox.dependency_injection.presentation.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Classes that can be injected by this Component
//    fun inject(activity: MainActivity)

    fun inject(fragment: ImagesFragment)
}