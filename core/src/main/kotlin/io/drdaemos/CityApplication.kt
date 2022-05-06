package io.drdaemos

import io.drdaemos.city.presentation.AbstractGameScreen
import io.drdaemos.city.presentation.WorldScreen
import ktx.app.KtxGame

class CityApplication : KtxGame<AbstractGameScreen>() {
    override fun create() {
        addScreen(WorldScreen())
        setScreen<WorldScreen>()
    }
}