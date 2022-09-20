package com.warriorsdev.dragonballsuper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.warriorsdev.dragonballsuper.R
import com.warriorsdev.dragonballsuper.data.character.CharacterDBS
import com.warriorsdev.dragonballsuper.ktx.exhaustive
import com.warriorsdev.dragonballsuper.ktx.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

    }

    private fun setupViews() {
        viewModel.getCharacter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, ::handle)
    }

    private fun handle(state: MainViewModel.State) {
        when (state) {
            //MainViewModel.State.CompleteRequirements -> enableNextButton()
            //MainViewModel.State.IncompleteRequirements -> {
            //    disableNextButton()
            //}
            is MainViewModel.State.ShowResults -> {
                showDataCurp(state.response)
            }
            //MainViewModel.State.Loading -> showLoading()
            //MainViewModel.State.HideKeyboard -> hideKeyboard()
            //MainViewModel.State.ShowError -> showError()
            //MainViewModel.State.SavedFailure -> showSavedFailed()
            //MainViewModel.State.SavedSuccess -> navigateOfDocument()
            else -> {}
        }.exhaustive
    }

    private fun showDataCurp(response: List<CharacterDBS>) {

        response.forEach {
            it.toString()
        }

        //enableGroup()
        //hideProgress()
        //onBoardingViewModel.setPeopleId(response.peopleId ?: "")
        //with(binding) {
        //    nameTextInputEditText.setText(response.firstName.plus(" ${response.middleName}"))
        //    firstNameTextInputEditText.setText(response.lastName)
        //    lastNameTextInputEditText.setText(response.secondLastName)
        //    genreTextInputEditText.setText(response.genre)
        //    bornDateTextInputEditText.setText(response.dateOfBirth)
        //}
        //validateInputs()
    }

}
