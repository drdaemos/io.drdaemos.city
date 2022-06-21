package io.drdaemos.city.spatial.decorators

import io.drdaemos.city.spatial.QuadTreeNode
import io.drdaemos.city.data.terrain.TerrainType

class WithTerrainType<T>(tree: QuadTreeNode<T>) : BaseQuadTreeDecorator<T>(tree) {
    lateinit var type: TerrainType
}