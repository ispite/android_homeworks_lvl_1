package ru.skillbox.dependency_injection.di

import dagger.Module
import ru.skillbox.dependency_injection.presentation.images.add.AddImageComponent
import ru.skillbox.dependency_injection.presentation.images.list.ImagesComponent

@Module(subcomponents = [ImagesComponent::class, AddImageComponent::class])
class AppSubcomponents {
}