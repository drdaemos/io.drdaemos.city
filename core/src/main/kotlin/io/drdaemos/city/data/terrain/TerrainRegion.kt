package io.drdaemos.city.data.terrain

import com.badlogic.gdx.math.EarClippingTriangulator
import io.drdaemos.city.spatial.aliases.Polygon

data class TerrainRegion (val polygon: Polygon, val type: TerrainType) {
    val vertices = polygon.vertices().map { listOf(it.x, it.y) }.flatten().toTypedArray().toFloatArray()
    val triangles: ShortArray = EarClippingTriangulator().computeTriangles(vertices).toArray()
}
