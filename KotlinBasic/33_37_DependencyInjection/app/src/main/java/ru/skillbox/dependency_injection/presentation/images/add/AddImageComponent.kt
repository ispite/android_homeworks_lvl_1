package ru.skillbox.dependency_injection.presentation.images.add

import dagger.Subcomponent
import ru.skillbox.dependency_injection.di.FragmentScope
import ru.skillbox.dependency_injection.presentation.images.list.ImagesComponent
import ru.skillbox.dependency_injection.presentation.images.list.ImagesFragment

@FragmentScope
@Subcomponent
interface AddImageComponent {

    // Factory to create instances of AddImageComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): AddImageComponent
    }

    fun inject(fragment: AddImageDialogFragment)
}