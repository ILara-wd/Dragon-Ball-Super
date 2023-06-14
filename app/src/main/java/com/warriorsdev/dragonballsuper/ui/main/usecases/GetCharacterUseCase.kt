package com.warriorsdev.dragonballsuper.ui.main.usecases

import com.warriorsdev.dragonballsuper.data.character.CharacterResponse
import com.warriorsdev.dragonballsuper.ui.main.repository.DragonBallRepository
import com.warriorsdev.dragonballsuper.domain.dispatchers.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val dragonBallRepository: DragonBallRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(
    ): List<CharacterResponse> = withContext(dispatcherProvider.default) {
        dragonBallRepository.getAllCharacter()
    }
}
