package io.drdaemos.city.presentation

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import ktx.scene2d.Scene2DSkin

abstract class AbstractGameScreen : KtxScreen {
    val batch = SpriteBatch()
    val inputHandler = InputMultiplexer()
    val skin = Skin(Gdx.files.internal("skin-default/skin/uiskin.json"))
    lateinit var uiStage: Stage
    lateinit var camera: OrthographicCamera

    override fun show() {
        // ui
        uiStage = Stage()
        Scene2DSkin.defaultSkin = skin
        constructUi(uiStage)

        // camera
        camera = OrthographicCamera()
        camera.setToOrtho(true)
        setupCamera(camera)

        // input
        inputHandler.setProcessors(uiStage)
        Gdx.input.inputProcessor = inputHandler
    }

    override fun render(delta: Float) {
        uiStage.act()
        batch.use(camera) {
            drawWithBatch(it, camera)
        }
        uiStage.draw()
    }

    override fun dispose() {
        batch.disposeSafely()
        uiStage.disposeSafely()
    }

    abstract fun constructUi(stage: Stage)
    abstract fun setupCamera(camera: OrthographicCamera)
    abstract fun drawWithBatch(batch: SpriteBatch, camera: OrthographicCamera)
}