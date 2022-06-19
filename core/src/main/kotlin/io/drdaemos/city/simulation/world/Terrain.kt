package io.drdaemos.city.simulation.world

import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.QuadTreeNode
import io.drdaemos.city.data.decorators.WithTerrainType

class Terrain (private val boundary: BoundingBox) {
    val regions = WithTerrainType(QuadTreeNode<Any>(boundary))
}