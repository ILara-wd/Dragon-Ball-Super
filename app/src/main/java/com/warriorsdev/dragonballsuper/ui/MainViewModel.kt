package com.warriorsdev.dragonballsuper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.warriorsdev.dragonballsuper.data.character.CharacterDBS
import com.warriorsdev.dragonballsuper.data.character.toDomain
import com.warriorsdev.dragonballsuper.ui.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private lateinit var charactersDBS: List<CharacterDBS>
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.IncompleteRequirements)
    val state: StateFlow<State> get() = _state

    fun getCharacter() {
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
        object HideKeyboard : State()
        object CompleteRequirements : State()
        object IncompleteRequirements : State()
        object ShowError : State()
        object SavedSuccess : State()
        object SavedFailure : State()
        object Default : State()

        data class ShowResults(val response: List<CharacterDBS>) : State()
    }

}
