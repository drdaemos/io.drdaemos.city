package io.drdaemos.city.data

interface QuadTreeNodeInterface {
    val box: BoundingBox
    fun insert(position: Position, value: Any): Boolean
    fun removeAt(position: Position): Boolean
    fun findValueAt(position: Position): Any?
}