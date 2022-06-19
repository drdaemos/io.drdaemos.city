package io.drdaemos.city.data.decorators

import io.drdaemos.city.data.*

abstract class BaseQuadTreeDecorator<T> (private val tree: QuadTreeNode<T>) : QuadTreeNodeInterface<T>, Iterable<QuadTreeNodeInterface<T>> {
    override fun insert(position: Position, value: T): Boolean = tree.insert(position, value)
    override fun removeAt(position: Position): Boolean = tree.removeAt(position)
    override fun findValueAt(position: Position): T? = tree.findValueAt(position)
    override fun findObjectsInside(area: BoundingBox): List<PositionedValue<T>> = tree.findObjectsInside(area)
    override fun findNearestObject(position: Position): PositionedValue<T>? = tree.findNearestObject(position)
    override fun iterator(): Iterator<QuadTreeNodeInterface<T>> = tree.iterator()
    override fun toString(): String = tree.toString()
}