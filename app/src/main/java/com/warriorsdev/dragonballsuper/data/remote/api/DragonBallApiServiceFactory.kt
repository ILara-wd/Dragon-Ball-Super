package com.warriorsdev.dragonballsuper.data.remote.api

import com.warriorsdev.dragonballsuper.data.di.DragonBallRetrofit
import com.warriorsdev.dragonballsuper.domain.remote.ServiceFactory
import retrofit2.Retrofit
import javax.inject.Inject

internal class DragonBallApiServiceFactory @Inject constructor(
    @DragonBallRetrofit private val retrofit: Retrofit,
) : ServiceFactory {

    override fun <T> createApiService(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}
