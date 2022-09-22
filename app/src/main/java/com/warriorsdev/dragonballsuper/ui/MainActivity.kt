package com.warriorsdev.dragonballsuper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.warriorsdev.dragonballsuper.R
import com.warriorsdev.dragonballsuper.data.character.CharacterDBS
import com.warriorsdev.dragonballsuper.databinding.ActivityMainBinding
import com.warriorsdev.dragonballsuper.ktx.viewBinding
import com.warriorsdev.dragonballsuper.ktx.observe
import com.warriorsdev.dragonballsuper.ktx.exhaustive
import com.warriorsdev.dragonballsuper.ktx.visible
import com.warriorsdev.dragonballsuper.ktx.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

    }

    private fun setupViews() {
        showLoading()
        viewModel.getCharacter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, ::handle)
    }

    private fun handle(state: MainViewModel.State) {
        when (state) {
            is MainViewModel.State.ShowResults -> {
                showDataCurp(state.response)
            }
            else -> {
                showLoading()
            }
        }.exhaustive
    }

    private fun showLoading() {
        with(binding) {
            progressCircular.container.visible()
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressCircular.container.gone()
        }
    }

    private fun showDataCurp(response: List<CharacterDBS>) {
        var textCharacterDBS = ""
        response.forEach {
            textCharacterDBS += it.name.plus("\n")
            Log.d("Dentro al forEach", "showDataCurp: ${it.name}")
        }

        binding.tvCharacter.text = textCharacterDBS
        Log.d("Dentro al forEach", "showDataCurp: $textCharacterDBS")
        hideLoading()
    }

}
