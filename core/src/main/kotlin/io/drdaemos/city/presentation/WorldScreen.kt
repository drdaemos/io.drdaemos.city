package io.drdaemos.city.presentation

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import io.drdaemos.city.data.*
import io.drdaemos.city.generation.RandomPointRegionGenerator
import io.drdaemos.city.simulation.world.WorldInstance
import ktx.graphics.circle
import ktx.graphics.use
import ktx.scene2d.actors
import ktx.scene2d.label

class WorldScreen : AbstractGameScreen() {
    private val world = WorldInstance()
    val shapeRenderer = ShapeRenderer()
    lateinit var pointsTree: QuadTreeNode
    lateinit var points: List<PositionedValue>
    lateinit var cameraPos: Label

    override fun constructUi(stage: Stage) {
        cameraPos = Label("???", skin)
        stage.actors {
        }
        stage.addActor(cameraPos)
    }

    override fun show() {
        super.show()

        val pointsGenerator = RandomPointRegionGenerator(128f, 128f, 100)
        pointsTree = pointsGenerator.generate()
        points = pointsTree.findObjectsInside(
            BoundingBox(Position(0f, 0f), Position(128f, 128f))
        )
    }

    override fun render(delta: Float) {
        super.render(delta)
        camera.update()
        cameraPos.setText(camera.position.toString())
        shapeRenderer.use(ShapeRenderer.ShapeType.Line, camera) {
            shapeRenderer.rect(0.0f, 0.0f, 128.0f, 128.0f)
            for (item in pointsTree) {
                when (item) {
                    is QuadTreeNode -> shapeRenderer.rect(item.box.topLeft.xPos, item.box.topLeft.yPos, item.box.getWidth(), item.box.getHeight())
                    is QuadTreeLeaf -> {
                        shapeRenderer.rect(item.box.topLeft.xPos, item.box.topLeft.yPos, item.box.getWidth(), item.box.getHeight())
                        for (point in item.children) {
                            shapeRenderer.circle(point.position.xPos, point.position.yPos, 1f)

                        }
                    }
                }
            }
        }
    }

    override fun setupCamera(camera: OrthographicCamera) {
        camera.zoom = .3f
        camera.position.set(64f, 64f, 0f)
        // noop
    }

    override fun drawWithBatch(batch: SpriteBatch, camera: OrthographicCamera) {
        // noop
    }
}