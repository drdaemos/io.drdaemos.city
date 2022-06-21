package io.drdaemos

import io.drdaemos.city.scenes.AbstractGameScreen
import io.drdaemos.city.scenes.WorldScreen
import ktx.app.KtxGame
import ktx.async.KtxAsync

class CityApplication : KtxGame<AbstractGameScreen>() {
    override fun create() {
        KtxAsync.initiate()
        addScreen(WorldScreen())
        setScreen<WorldScreen>()
    }
}