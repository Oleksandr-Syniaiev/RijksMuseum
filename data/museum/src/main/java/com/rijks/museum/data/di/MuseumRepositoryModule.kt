package com.rijks.museum.data.di

import com.rijks.museum.data.repository.RemoteMuseumRepository
import com.rijks.museum.data.source.remote.DefaultRemoteMuseumDataSource
import com.rijks.museum.data.source.remote.RemoteMuseumDataSource
import com.rijks.museum.domain.repository.MuseumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MuseumRepositoryModule {
    @Binds
    abstract fun bindMuseumRepository(museumRepository: RemoteMuseumRepository): MuseumRepository

    @Binds
    abstract fun bindRemoteMuseumDataSource(remoteDataSource: DefaultRemoteMuseumDataSource): RemoteMuseumDataSource
}
