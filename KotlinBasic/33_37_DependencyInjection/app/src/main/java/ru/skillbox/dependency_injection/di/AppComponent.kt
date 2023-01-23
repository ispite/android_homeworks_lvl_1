package ru.skillbox.dependency_injection.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.skillbox.dependency_injection.presentation.images.add.AddImageComponent
import ru.skillbox.dependency_injection.presentation.images.list.ImagesComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppSubcomponents::class, ImagesModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun imagesComponent(): ImagesComponent.Factory

    fun addImagesComponent(): AddImageComponent.Factory

}