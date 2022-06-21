package io.drdaemos.city.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonBatch
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import io.drdaemos.city.entities.EntityInterface
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.scene2d.Scene2DSkin

abstract class AbstractGameScreen : KtxScreen {
    val skin = Skin(Gdx.files.internal("skin-default/skin/uiskin.json"))
    val input = InputMultiplexer()
    val entities = mutableListOf<EntityInterface>()
    lateinit var uiStage: Stage
    lateinit var camera: OrthographicCamera
    lateinit var batch: PolygonBatch
    lateinit var context: RenderingContext

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
        input.setProcessors(uiStage)
        Gdx.input.inputProcessor = input

        // batch
        batch = PolygonSpriteBatch()

        // context
        context = RenderingContext(0f, batch, camera, input, entities)
    }

    override fun render(delta: Float) {
        context.delta = delta
        uiStage.act()
        runSystems(context)
        uiStage.draw()
    }

    override fun dispose() {
        batch.disposeSafely()
        uiStage.disposeSafely()
    }

    abstract fun constructUi(stage: Stage)
    abstract fun setupCamera(camera: OrthographicCamera)
    abstract fun runSystems(context: RenderingContext)
}