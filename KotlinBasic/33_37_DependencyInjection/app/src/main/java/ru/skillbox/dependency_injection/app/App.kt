package ru.skillbox.dependency_injection.app

import android.app.Application
import ru.skillbox.dependency_injection.di.AppComponent
import ru.skillbox.dependency_injection.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}