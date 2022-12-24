package com.dicoding.jetheroes.data

import com.dicoding.jetheroes.model.Hero
import com.dicoding.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }

    fun searchHeroes(query: String): List<Hero> {
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    fun getHeroById(heroId: String): Hero {
        return HeroesData.heroes.first {
            it.id == heroId
        }
    }
}