package io.drdaemos.city.scenes

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import io.drdaemos.city.generation.TerrainGenerator
import io.drdaemos.city.entities.world.Terrain
import io.drdaemos.city.systems.TerrainRenderer
import ktx.actors.onClick
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.textButton

class WorldScreen : AbstractGameScreen() {
    private val terrainGenerator = TerrainGenerator()
    private val terrainRenderer = TerrainRenderer()
    lateinit var cameraPos: Label

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
        val regions = terrainGenerator.generate()
        val terrain = Terrain.createFromRegions(regions)

        entities.add(terrain)
    }

    override fun runSystems(context: RenderingContext) {
        camera.update()
        cameraPos.setText(camera.position.toString() + " - " + camera.zoom.toString())

        terrainRenderer.update(context)
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
}