package com.ravinada.riomoneyassignment

import android.app.Application
import com.ravinada.riomoneyassignment.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * The [Application] class for the Rio Money app.
 *
 * This class serves as the entry point for the application. It initializes Koin for
 * dependency injection and provides a static method to access the application context.
 */
class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Koin for dependency injection
        startKoin {
            androidContext(this@App)
            // Load all Koin modules defined in the Modules class
            modules(Modules.getAll())
        }
    }
}
