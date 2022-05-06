package io.drdaemos.city.presentation

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import io.drdaemos.city.simulation.world.WorldInstance
import ktx.graphics.circle
import ktx.graphics.use
import ktx.scene2d.actors
import ktx.scene2d.label

class WorldScreen : AbstractGameScreen() {
    private val world = WorldInstance()
    val shapeRenderer = ShapeRenderer()
    var circles = mutableListOf<Triple<Float, Float, Float>>()
    lateinit var cameraPos: Label

    override fun constructUi(stage: Stage) {
        cameraPos = Label("???", skin)
        stage.actors {
        }
        stage.addActor(cameraPos)
    }

    override fun show() {
        super.show()

        for (i in 1..100) {
            circles.add(
                Triple(random.nextFloat() * 128.0f, random.nextFloat() * 128.0f, random.nextFloat() * 10.0f)
            )
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        camera.update()
        cameraPos.setText(camera.position.toString())
        shapeRenderer.use(ShapeRenderer.ShapeType.Line, camera) {
            for (item in circles) {
                shapeRenderer.rect(0.0f, 0.0f, 128.0f, 128.0f)
                shapeRenderer.circle(item.first, item.second, item.third)
            }
        }
    }

    override fun setupCamera(camera: OrthographicCamera) {
        camera.zoom = .4f
        camera.position.set(64f, 64f, 0f)
        // noop
    }

    override fun drawWithBatch(batch: SpriteBatch, camera: OrthographicCamera) {
        // noop
    }
}