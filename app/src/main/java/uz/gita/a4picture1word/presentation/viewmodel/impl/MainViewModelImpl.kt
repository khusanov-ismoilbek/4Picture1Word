package uz.gita.a4picture1word.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.a4picture1word.presentation.viewmodel.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(): ViewModel(), MainViewModel {
    override val onCLickLottieLiveData = MutableLiveData<Unit>()
    override val onClickRateLiveData = MutableLiveData<Unit>()
    override val onClickSettingsLiveData = MutableLiveData<Unit>()

    override fun onClickLottie() {
        onCLickLottieLiveData.value = Unit
    }

    override fun onClickRate() {
        onClickRateLiveData.value = Unit
    }

    override fun onCLickSettings() {
        onClickSettingsLiveData.value = Unit
    }
}