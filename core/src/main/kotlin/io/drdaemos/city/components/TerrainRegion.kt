package io.drdaemos.city.components

import com.badlogic.gdx.math.EarClippingTriangulator
import io.drdaemos.city.data.TerrainType
import io.drdaemos.city.data.spatial.Polygon

data class TerrainRegion (val polygon: Polygon, val type: TerrainType) {
    val vertices = polygon.vertices().map { listOf(it.x, it.y) }.flatten().toTypedArray().toFloatArray()
    val triangles = EarClippingTriangulator().computeTriangles(vertices).toArray()
}
