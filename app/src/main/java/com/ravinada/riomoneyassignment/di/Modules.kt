package com.ravinada.riomoneyassignment.di

import android.app.Application
import android.content.Context
import com.ravinada.riomoneyassignment.ui.home.HomeViewModel
import com.ravinada.riomoneyassignment.R
import com.ravinada.riomoneyassignment.data.api.repository.Api
import com.ravinada.riomoneyassignment.data.api.repository.ApiRepository
import com.ravinada.riomoneyassignment.data.api.repository.ApiRepositoryImpl
import com.ravinada.riomoneyassignment.data.retrofit.NetworkBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin modules for dependency injection.
 */
object Modules {
    // Application-related modules
    private val applicationModules = module {
        single {
            get<Application>().run {
                getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            }
        }
    }

    // ViewModels modules
    private val viewModelModules = module {
        viewModel { HomeViewModel(get()) }
    }

    // Network-related modules
    private val networkModules = module {
        single { NetworkBuilder.create(NetworkBuilder.BASE_URL, Api::class.java) }
    }

    // Repository-related modules
    private val repositoryModules = module {
        single<ApiRepository> { ApiRepositoryImpl(get()) }
    }

    // Data Access Object (DAO) modules (if applicable)
    private val daoModules = module {
        // Define DAO-related dependencies here
    }

    /**
     * Get all modules to be used for dependency injection.
     *
     * @return List of Koin modules.
     */
    fun getAll() =
        listOf(applicationModules, viewModelModules, networkModules, repositoryModules, daoModules)
}
