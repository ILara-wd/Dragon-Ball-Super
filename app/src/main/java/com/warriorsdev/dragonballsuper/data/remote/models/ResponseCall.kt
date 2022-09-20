package com.warriorsdev.dragonballsuper.data.remote.models

import com.warriorsdev.dragonballsuper.data.remote.mappers.ClientExceptionMapper
import retrofit2.Call
import retrofit2.Callback

internal class ResponseCall(
    private val delegate: Call<Any>,
    private val clientExceptionMapper: ClientExceptionMapper,
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>): Unit =
        delegate.enqueue(ResponseCallback(callback, clientExceptionMapper))

    override fun clone(): Call<Any> = ResponseCall(delegate.clone(), clientExceptionMapper)
}
