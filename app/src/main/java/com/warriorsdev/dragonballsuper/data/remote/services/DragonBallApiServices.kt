package com.warriorsdev.dragonballsuper.data.remote.services

import com.warriorsdev.dragonballsuper.data.character.CharacterResponse
import com.warriorsdev.dragonballsuper.data.remote.services.DragonBallServiceNames.CHARACTER
import retrofit2.http.GET

interface DragonBallApiServices {

    @GET(CHARACTER)
    suspend fun serviceCharacterList(): List<CharacterResponse>


}

