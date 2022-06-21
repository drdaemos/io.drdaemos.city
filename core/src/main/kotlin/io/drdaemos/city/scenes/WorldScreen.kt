package io.drdaemos.city.scenes

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.generation.TerrainGenerator
import io.drdaemos.city.entities.world.Terrain
import io.drdaemos.city.systems.TerrainRenderer
import ktx.actors.onClick
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.textButton


class WorldScreen : AbstractGameScreen() {
    private val seed = 1000L
    private val box = BoundingBox(2048f, 1024f)
    private val terrainGenerator = TerrainGenerator(box, seed)
    private val terrainRenderer = TerrainRenderer()
    lateinit var cameraPos: Label
    lateinit var loading: Label

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
            loading = label("Please wait...") {
                x = stage.width / 2 - this.prefWidth / 2
                y = stage.height / 2 - this.prefHeight / 2
            }
            cameraPos = label("") {
                x = 10f
                y = 10f
            }
        }
    }

    override fun show() {
        super.show()

        runAsync {
            generateWorldState()
            loading.isVisible = false
        }
    }

    private fun generateWorldState() {
        val regions = terrainGenerator.randomIsland(10000)
        val terrain = Terrain.createFromRegions(regions, box)

        entities.add(terrain)
    }

    override fun runSystems(context: RenderingContext) {
        camera.update()
        cameraPos.setText(camera.position.toString() + " - " + camera.zoom.toString())

        terrainRenderer.update(context)
    }

    override fun setupCamera(camera: OrthographicCamera) {
        camera.zoom = 1.5f
        camera.position.set(box.getCenter().x, box.getCenter().y, 0f)
        // noop
    }

    private fun increaseZoom() {
        if (camera.zoom > 0f) {
            camera.zoom -= camera.zoom * .25f
        }
    }

    private fun decreaseZoom() {
        if (camera.zoom > 0f) {
            camera.zoom += camera.zoom * .25f
        }
    }
}