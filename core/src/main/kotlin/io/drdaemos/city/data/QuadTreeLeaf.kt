package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.OutOfBoundsException
import io.drdaemos.city.data.exceptions.PositionNotEmptyException

const val LEAF_CAPACITY = 4

data class QuadTreeLeaf<T> (val box: BoundingBox): QuadTreeNodeInterface<T> {
    val children: MutableList<PositionedValue<T>> = mutableListOf()

    override fun insert(position: Position, value: T): Boolean {
        if (!box.contains(position)) {
            throw OutOfBoundsException()
        }

        if (contains(position)) {
            throw PositionNotEmptyException()
        }

        if (children.count() == LEAF_CAPACITY) {
            return false
        }

        children.add(PositionedValue(position, value))
        return true
    }

    override fun removeAt(position: Position): Boolean {
        return children.removeIf { it.position == position }
    }

    override fun findValueAt(position: Position): T? {
        return children.find { it.position == position }?.value
    }

    override fun findObjectsInside(area: BoundingBox): List<PositionedValue<T>> {
        return children.filter { area.contains(it.position) }
    }

    override fun findNearestObject(position: Position): PositionedValue<T>? {
        return children.minByOrNull { it.position.dist(position) }
    }

    fun count(): Int {
        return children.size
    }

    fun contains(position: Position): Boolean {
        return children.any { it.position == position }
    }

    override fun toString(): String {
        return "Leaf: $box, ${count()}"
    }
}