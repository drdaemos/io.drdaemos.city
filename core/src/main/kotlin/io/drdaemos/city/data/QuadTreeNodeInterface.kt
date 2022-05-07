package io.drdaemos.city.data

interface QuadTreeNodeInterface {
    val box: BoundingBox
    val parent: QuadTreeNodeInterface?
    fun insert(position: Position, value: Any): Boolean
    fun removeAt(position: Position): Boolean
    fun findValueAt(position: Position): Any?
    fun findObjectsInside(area: BoundingBox): List<PositionedValue>
}