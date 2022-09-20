package com.warriorsdev.dragonballsuper.data.remote.mappers

import com.google.gson.Gson
import com.warriorsdev.dragonballsuper.data.remote.models.ClientException
import com.warriorsdev.dragonballsuper.data.remote.models.ErrorBody
import com.warriorsdev.dragonballsuper.domain.mapper.Mapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class ClientExceptionMapper @Inject constructor(
    private val gson: Gson,
) : Mapper<Throwable, ClientException> {

    override fun map(value: Throwable): ClientException = when (value) {
        is HttpException -> {
            try {
                val errorBody = gson.fromJson(
                    value.response()?.errorBody()?.string(),
                    ErrorBody::class.java
                )
                ClientException.ApiError(errorBody.message, value.response()?.code() ?: -1)
            } catch (exception: Exception) {
                ClientException.UnknownError(exception)
            }
        }
        is IOException -> ClientException.NetworkError
        else -> ClientException.UnknownError(value)
    }
}
