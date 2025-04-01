package com.rijks.museum.di

import android.content.Context
import com.rijks.museum.core.ui.navigation.DefaultNavigator
import com.rijks.museum.core.ui.navigation.Navigator
import com.rijks.museum.core.ui.navigation.Route
import com.rijks.museum.core.utils.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun providesContext(
        @ApplicationContext context: Context,
    ): Context = context

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesNavigator(): Navigator = DefaultNavigator(startDestination = Route.ListOfArtsScreen)
}
