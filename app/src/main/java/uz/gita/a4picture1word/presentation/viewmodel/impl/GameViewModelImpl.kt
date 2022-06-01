package uz.gita.a4picture1word.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.a4picture1word.data.model.GameData
import uz.gita.a4picture1word.domain.repository.AppRepository
import uz.gita.a4picture1word.presentation.viewmodel.GameViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModelImpl @Inject constructor(private val repository: AppRepository) : ViewModel(),
    GameViewModel {
    override val onClickBackLiveData = MutableLiveData<Unit>()
    override val setVariantsLiveData = MutableLiveData<Unit>()
    override val setAnswerLengthLiveData = MutableLiveData<Int>()
    override val setImagesLiveData = MutableLiveData<List<Int>>()
    override val clearAnswerLiveData = MutableLiveData<Unit>()
    override val setCoinsLiveData = MutableLiveData<Int>()
    override val setCurrentLevelLiveData = MutableLiveData<Int>()
    override val onClickVariantsLiveData = MutableLiveData<Int>()
    override val onClickAnswerLiveData = MutableLiveData<Int>()

    override fun onClickBack() {
        onClickBackLiveData.value = Unit
    }

    override fun setVariants() {
        setVariantsLiveData.value = Unit
    }

    override fun setAnswerLength(level: Int) {
        setAnswerLengthLiveData.value = repository.getData(level).answer.length
    }

    override fun setImages(level: Int) {
        val images = ArrayList<Int>()
        images.clear()
        images.add(repository.getData(level).image1)
        images.add(repository.getData(level).image2)
        images.add(repository.getData(level).image3)
        images.add(repository.getData(level).image4)
        setImagesLiveData.value = images
    }

    override fun clearAnswer() {
        clearAnswerLiveData.value = Unit
    }

    override fun setCoins(coins: Int) {
        setCoinsLiveData.value = coins
    }

    override fun setCurrentLevel(level: Int) {
        setCurrentLevelLiveData.value = level
    }

    override fun getDataByLevel(level: Int): GameData {
        return repository.getData(level)
    }

    override fun onClickVariant(pos: Int) {
        onClickVariantsLiveData.value = pos
    }

    override fun onClickAnswer(pos: Int) {
        onClickAnswerLiveData.value = pos
    }

    override fun getSize(): Int {
        return repository.getSize()
    }

}