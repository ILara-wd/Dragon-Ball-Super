package com.warriorsdev.dragonballsuper.ui.main.repository

import com.warriorsdev.dragonballsuper.data.character.CharacterResponse
import com.warriorsdev.dragonballsuper.ui.main.DragonBallRemoteDataSource
import javax.inject.Inject

class DragonBallRepository @Inject constructor(
    private val dragonBallRemoteDataSource: DragonBallRemoteDataSource
) {
    suspend fun getAllCharacter(): List<CharacterResponse> {
        return dragonBallRemoteDataSource.getAllCharacter()
    }
}
