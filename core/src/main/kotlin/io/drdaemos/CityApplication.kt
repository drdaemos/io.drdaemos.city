package io.drdaemos

import io.drdaemos.city.scenes.AbstractGameScreen
import io.drdaemos.city.scenes.WorldScreen
import ktx.app.KtxGame

class CityApplication : KtxGame<AbstractGameScreen>() {
    override fun create() {
        addScreen(WorldScreen())
        setScreen<WorldScreen>()
    }
}