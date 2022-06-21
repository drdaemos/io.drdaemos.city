package io.drdaemos.city.components

import io.drdaemos.city.data.terrain.TerrainRegion
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.entities.EntityInterface

data class TerrainData(override val entity: EntityInterface, val regions: List<TerrainRegion>, val box: BoundingBox) : ComponentInterface