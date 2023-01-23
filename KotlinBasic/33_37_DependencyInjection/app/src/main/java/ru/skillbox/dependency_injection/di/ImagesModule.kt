package ru.skillbox.dependency_injection.di

import dagger.Binds
import dagger.Module
import ru.skillbox.dependency_injection.data.ImagesRepository
import ru.skillbox.dependency_injection.data.ImagesRepositoryImpl

// Tells Dagger this is a Dagger module
// Because of @Binds, StorageModule needs to be an abstract class
@Module
abstract class ImagesModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideImagesRepository(imagesRepositoryImpl: ImagesRepositoryImpl): ImagesRepository
}