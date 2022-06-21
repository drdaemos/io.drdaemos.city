package io.drdaemos.city.entities.world

import io.drdaemos.city.components.TerrainData
import io.drdaemos.city.components.TerrainRegion
import io.drdaemos.city.entities.AbstractEntity

class Terrain : AbstractEntity() {
    companion object {
        fun createFromRegions(regions: List<TerrainRegion>): Terrain {
            val terrain = Terrain()
            val terrainData = TerrainData(terrain, regions)
            terrain.components[TerrainData::class] = terrainData

            return terrain
        }
    }
}