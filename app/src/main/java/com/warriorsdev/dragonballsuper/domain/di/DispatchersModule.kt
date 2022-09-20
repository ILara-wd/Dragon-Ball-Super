package com.warriorsdev.dragonballsuper.domain.di

import com.warriorsdev.dragonballsuper.domain.dispatchers.DefaultDispatcherProvider
import com.warriorsdev.dragonballsuper.domain.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DispatchersModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        defaultDispatcherProvider: DefaultDispatcherProvider,
    ): DispatcherProvider
}
