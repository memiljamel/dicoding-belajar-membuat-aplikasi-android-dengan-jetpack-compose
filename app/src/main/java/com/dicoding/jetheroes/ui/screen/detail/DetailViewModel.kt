package com.dicoding.jetheroes.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.dicoding.jetheroes.data.HeroRepository
import com.dicoding.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(private val repository: HeroRepository) : ViewModel() {

    fun getHeroById(heroId: String): StateFlow<Hero> {
        return MutableStateFlow(
            repository.getHeroById(heroId)
        ).asStateFlow()
    }
}