package io.drdaemos.city.components

import io.drdaemos.city.entities.EntityInterface

data class TerrainData(override val entity: EntityInterface, val regions: List<TerrainRegion>) : ComponentInterface