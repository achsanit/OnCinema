package com.achsanit.oncinema.di

import com.achsanit.oncinema.data.repository.MainRepository
import com.achsanit.oncinema.data.source.remote.services.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepo(apiHelper: ApiHelper): MainRepository {
        return MainRepository(apiHelper)
    }
}