package io.drdaemos.city.data.decorators

import io.drdaemos.city.data.QuadTreeNode
import io.drdaemos.city.data.Terrain

class WithTerrainType<T>(tree: QuadTreeNode<T>) : BaseQuadTreeDecorator<T>(tree) {
    lateinit var type: Terrain
}