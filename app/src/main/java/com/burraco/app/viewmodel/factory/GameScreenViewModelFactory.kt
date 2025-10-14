package com.burraco.app.viewmodel.factory

import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.lifecycle.ViewModelProvider
import com.burraco.app.viewmodel.BurracoViewModel
import com.burraco.app.viewmodel.GameScreenViewModel

class GameScreenViewModelFactory(
    private val burracoViewModel: BurracoViewModel,
): ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScreenViewModel::class.java)) {
            return GameScreenViewModel(burracoViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}