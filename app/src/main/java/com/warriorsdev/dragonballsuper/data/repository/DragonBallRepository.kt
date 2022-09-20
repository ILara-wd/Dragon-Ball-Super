package com.warriorsdev.dragonballsuper.data.repository

import com.warriorsdev.dragonballsuper.data.character.CharacterResponse
import com.warriorsdev.dragonballsuper.data.remote.DragonBallRemoteDataSource
import javax.inject.Inject

class DragonBallRepository @Inject constructor(
    private val dragonBallRemoteDataSource: DragonBallRemoteDataSource
) {
    suspend fun getAllCharacter(): List<CharacterResponse> {
        return dragonBallRemoteDataSource.getAllCharacter()
    }
}
