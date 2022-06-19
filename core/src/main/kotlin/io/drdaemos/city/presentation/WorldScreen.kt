package io.drdaemos.city.presentation

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import io.drdaemos.city.data.*
import io.drdaemos.city.generation.TerrainGenerator
import io.drdaemos.city.generation.noise.BlueNoiseGenerator
import io.drdaemos.city.simulation.world.WorldInstance
import ktx.actors.onClick
import ktx.graphics.use
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.textButton


class WorldScreen : AbstractGameScreen() {
    private val world = WorldInstance()
    val shapeRenderer = ShapeRenderer()
    val terrainGenerator = TerrainGenerator()
    val blueNoiseGenerator = BlueNoiseGenerator(BoundingBox(Position(0f,0f), Position(1024f, 1024f)))
    lateinit var cameraPos: Label
    lateinit var map : List<PositionedValue<Color>>
    lateinit var randomPoints : List<Position>

    override fun constructUi(stage: Stage) {
        stage.actors {
            textButton("zoom +") {
                x = 10f
                y = 60f
                width = 65f
                onClick { increaseZoom() }
            }
            textButton("zoom -") {
                x = 10f
                y = 30f
                width = 65f
                onClick { decreaseZoom() }
            }
            cameraPos = label("") {
                x = 10f
                y = 10f
            }
        }
    }

    override fun show() {
        super.show()
        map = terrainGenerator.generate()
        randomPoints = blueNoiseGenerator.randomList(750)
    }

    override fun render(delta: Float) {
        camera.update()
        cameraPos.setText(camera.position.toString() + " - " + camera.zoom.toString())

//        shapeRenderer.use(ShapeRenderer.ShapeType.Filled, camera) {
//            for (item in map) {
//                shapeRenderer.color = item.value as Color
//                shapeRenderer.rect(item.position.x, item.position.y, 1f, 1f)
//            }
//        }

        shapeRenderer.use(ShapeRenderer.ShapeType.Filled, camera) {
            shapeRenderer.setColor(1f,1f,1f,1f)
            for (item in randomPoints) {
                shapeRenderer.circle(item.x, item.y, 10f)
            }
        }

        super.render(delta)
    }

    override fun setupCamera(camera: OrthographicCamera) {
        camera.zoom = 2f
        camera.position.set(512f, 512f, 0f)
        // noop
    }

    fun increaseZoom() {
        if (camera.zoom > 0f) {
            camera.zoom -= camera.zoom * .25f
        }
    }

    fun decreaseZoom() {
        if (camera.zoom > 0f) {
            camera.zoom += camera.zoom * .25f
        }
    }

    override fun drawWithBatch(batch: SpriteBatch, camera: OrthographicCamera) {
        // noop
    }
}