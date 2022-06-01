package uz.gita.a4picture1word.presentation.viewmodel

import androidx.lifecycle.LiveData

interface MainViewModel {
    val onCLickLottieLiveData: LiveData<Unit>
    val onClickRateLiveData: LiveData<Unit>
    val onClickSettingsLiveData: LiveData<Unit>

    fun onClickLottie()
    fun onClickRate()
    fun onCLickSettings()
}