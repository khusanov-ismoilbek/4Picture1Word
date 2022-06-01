package uz.gita.a4picture1word.domain.repository

import uz.gita.a4picture1word.data.model.GameData

interface AppRepository {
    fun getData(pos: Int): GameData
    fun getSize(): Int
}