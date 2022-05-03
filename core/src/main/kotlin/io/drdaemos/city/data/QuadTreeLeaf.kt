package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.OutOfBoundsException
import io.drdaemos.city.data.exceptions.PositionNotEmptyException

const val LEAF_CAPACITY = 4

data class QuadTreeLeaf (override val box: BoundingBox): QuadTreeNodeInterface {
    private val children: MutableList<Pair<Position, Any>> = mutableListOf()

    override fun insert(position: Position, value: Any): Boolean {
        if (!box.contains(position)) {
            throw OutOfBoundsException()
        }

        if (contains(position)) {
            throw PositionNotEmptyException()
        }

        if (children.count() == LEAF_CAPACITY) {
            return false
        }

        children.add(Pair(position, value))
        return true
    }

    override fun removeAt(position: Position): Boolean {
        return children.removeIf { it.first == position }
    }

    fun getChildren(): MutableList<Pair<Position, Any>> {
        return children
    }

    fun count(): Int {
        return children.size
    }

    fun contains(position: Position): Boolean {
        return children.any { it.first == position }
    }

    override fun findValueAt(position: Position): Any? {
        return children.find { it.first == position }?.second
    }
}