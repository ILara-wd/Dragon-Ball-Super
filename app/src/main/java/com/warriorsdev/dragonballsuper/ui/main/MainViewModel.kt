package com.warriorsdev.dragonballsuper.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warriorsdev.dragonballsuper.data.character.CharacterDBS
import com.warriorsdev.dragonballsuper.data.character.toDomain
import com.warriorsdev.dragonballsuper.ui.main.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private var charactersDBS: List<CharacterDBS> = emptyList()
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.IncompleteRequirements)
    val state: StateFlow<State> get() = _state

    fun getCharacter() {
        _state.value = State.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                getCharacterUseCase()
            }
                .onSuccess { response ->
                    if (response.isNotEmpty()) {
                        charactersDBS = response.map { it.toDomain() }
                        _state.value = State.ShowResults(charactersDBS)
                    } else {
                        _state.value = State.ShowError
                    }
                }
                .onFailure {
                    _state.value = State.ShowError
                }
        }
    }

    sealed class State {
        object Loading : State()
        object IncompleteRequirements : State()
        object ShowError : State()
        data class ShowResults(val response: List<CharacterDBS>) : State()
    }

}
