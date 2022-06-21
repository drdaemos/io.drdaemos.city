package io.drdaemos.city.scenes

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.PolygonBatch
import io.drdaemos.city.entities.EntityInterface
import ktx.graphics.use

data class RenderingContext(var delta: Float, val batch: PolygonBatch, val camera: OrthographicCamera, val input: InputProcessor, val entities: List<EntityInterface>) {
    fun render(function: (batch: PolygonBatch) -> Unit) {
        batch.use(camera) {
            function(batch)
        }
    }
}