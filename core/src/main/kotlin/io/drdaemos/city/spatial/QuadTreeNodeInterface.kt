package io.drdaemos.city.spatial

interface QuadTreeNodeInterface<T> {
    fun insert(position: Position, value: T): Boolean
    fun removeAt(position: Position): Boolean
    fun findValueAt(position: Position): T?
    fun findObjectsInside(area: BoundingBox): List<PositionedValue<T>>
    fun findNearestObject(position: Position): PositionedValue<T>?
}