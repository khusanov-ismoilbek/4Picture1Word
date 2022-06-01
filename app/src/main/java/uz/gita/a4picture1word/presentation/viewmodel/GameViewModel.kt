package uz.gita.a4picture1word.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.a4picture1word.data.model.GameData

interface GameViewModel {
    val onClickBackLiveData: LiveData<Unit>
    val setVariantsLiveData: LiveData<Unit>
    val setAnswerLengthLiveData: LiveData<Int>
    val setImagesLiveData: LiveData<List<Int>>
    val clearAnswerLiveData: LiveData<Unit>
    val setCoinsLiveData: LiveData<Int>
    val setCurrentLevelLiveData: LiveData<Int>
    val onClickVariantsLiveData: LiveData<Int>
    val onClickAnswerLiveData: LiveData<Int>

    fun onClickBack()
    fun setVariants()
    fun setAnswerLength(level: Int)
    fun setImages(level: Int)
    fun clearAnswer()
    fun setCoins(coins: Int)
    fun setCurrentLevel(level: Int)
    fun getDataByLevel(level: Int): GameData
    fun onClickVariant(pos: Int)
    fun onClickAnswer(pos: Int)

    fun getSize(): Int
}