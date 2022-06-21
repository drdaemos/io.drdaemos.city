package io.drdaemos.city.entities.world

import io.drdaemos.city.components.TerrainData
import io.drdaemos.city.data.terrain.TerrainRegion
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.entities.AbstractEntity

class Terrain : AbstractEntity() {
    companion object {
        fun createFromRegions(regions: List<TerrainRegion>, box: BoundingBox): Terrain {
            val terrain = Terrain()
            val terrainData = TerrainData(terrain, regions, box)
            terrain.components[TerrainData::class] = terrainData

            return terrain
        }
    }
}