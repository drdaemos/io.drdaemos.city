package io.drdaemos.city.data

data class QuadTreeLeaf (override val box: BoundingBox, val position: Position, val value: Any): QuadTreeNodeInterface {
    fun contains(lookup: Position): Boolean {
        return lookup == position
    }
}