package ru.skillbox.dependency_injection.presentation.images.list

import dagger.Subcomponent
import ru.skillbox.dependency_injection.di.FragmentScope

@FragmentScope
@Subcomponent
interface ImagesComponent {

    // Factory to create instances of RegistrationComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): ImagesComponent
    }

    fun inject(fragment: ImagesFragment)

}