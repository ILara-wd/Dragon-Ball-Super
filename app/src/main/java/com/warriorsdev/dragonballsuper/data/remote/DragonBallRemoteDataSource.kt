package com.warriorsdev.dragonballsuper.data.remote

import com.warriorsdev.dragonballsuper.data.character.CharacterResponse
import com.warriorsdev.dragonballsuper.data.remote.services.DragonBallApiServices
import javax.inject.Inject

class DragonBallRemoteDataSource @Inject constructor(private val dragonBallApiServices: DragonBallApiServices) {

    suspend fun getAllCharacter(): List<CharacterResponse> {
        return dragonBallApiServices.serviceCharacterList()
    }

}
