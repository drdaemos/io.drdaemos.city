package io.drdaemos.city.simulation.world

import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.spatial.Polygon

class Terrain (private val box: BoundingBox) {
    lateinit var regions: Collection<TerrainRegion>
}