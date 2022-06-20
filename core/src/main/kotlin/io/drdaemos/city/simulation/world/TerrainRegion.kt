package io.drdaemos.city.simulation.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.EarClippingTriangulator
import io.drdaemos.city.data.TerrainType
import io.drdaemos.city.data.spatial.Polygon

class TerrainRegion (val polygon: Polygon, val type: TerrainType) {

    val vertices = polygon.vertices().map { listOf(it.x, it.y) }.flatten().toTypedArray().toFloatArray()

    fun mapTypeToColor(): Color {
        return when (type) {
            TerrainType.Hills -> Color(130 / 255f, 180 / 255f, 100 / 255f, 1f) // hills
            TerrainType.Plains -> Color(99 / 255f, 147 / 255f, 77 / 255f, 1f) // plains
            TerrainType.Sand -> Color(0xF1 / 255f, 0xD5 / 255f, 0xAE / 255f, 1f) // sand
            else -> Color(28 / 255f, 104 / 255f, 155 / 255f, 1f) // water
        }
    }

    fun region(): PolygonRegion {
        val pix = Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(mapTypeToColor());
        pix.fill();
        return PolygonRegion(
            TextureRegion(Texture(pix)),
            vertices,
            EarClippingTriangulator().computeTriangles(vertices).toArray()
        )
    }
}