package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.OutOfBoundsException
import io.drdaemos.city.data.exceptions.PositionNotEmptyException

/**
 * Quad Tree is a 2d positioning structure with 0:0 point in top-left corner
 *
 *   0 1 2 . x        NW | NE
 * 0 . . . .          ---|---
 * 1 . . o .          SW | SE
 * 2 . . . .
 * . . o . .
 * y
 */
class QuadTreeNode (override val box: BoundingBox) : QuadTreeNodeInterface {

    private val quadrants: MutableMap<Quadrants, QuadTreeNodeInterface> = mutableMapOf()

    fun insert(position: Position, value: Any): Boolean {
        if (!box.contains(position)) {
            throw OutOfBoundsException()
        }

        return when {
            tryInsertInto(Quadrants.NorthWest, position, value)
                    || tryInsertInto(Quadrants.NorthEast, position, value)
                    || tryInsertInto(Quadrants.SouthEast, position, value)
                    || tryInsertInto(Quadrants.SouthWest, position, value) -> true
            else -> false
        }
    }

    fun removeAt(position: Position): Boolean {
        if (!box.contains(position)) {
            return false
        }

        for ((direction, node) in quadrants) {
            if (node is QuadTreeLeaf && node.contains(position)) {
                quadrants.remove(direction)
                return true
            } else if (node is QuadTreeNode && node.removeAt(position)) {
                // contract 1-child nodes
                if (node.quadrants.count() == 1) {
                    val lastKey = node.quadrants.keys.first()
                    quadrants.replace(direction, node.quadrants[lastKey]!!)
                }
                return true
            } else {
                continue
            }
        }

        return false
    }

    fun findValueAt(lookup: Position): Any? {
        if (!box.contains(lookup)) {
            return null
        }

        for ((direction, node) in quadrants) {
            if (node is QuadTreeLeaf && node.contains(lookup)) {
                return node.value
            } else if (node is QuadTreeNode) {
                val value = node.findValueAt(lookup)
                if (value != null) return value else continue
            }
        }

        return null
    }

    fun findObjectsInside(box: BoundingBox) {

    }

    private fun tryInsertInto(direction: Quadrants, position: Position, value: Any): Boolean {
        val quadBox = when (direction) {
            Quadrants.NorthWest -> getNorthWestBox()
            Quadrants.NorthEast -> getNorthEastBox()
            Quadrants.SouthEast -> getSouthEastBox()
            Quadrants.SouthWest -> getSouthWestBox()
        }

        if (!quadBox.contains(position)) {
            return false
        }

        if (quadrants.contains(direction)) {
            if (quadrants[direction] is QuadTreeNode) {
                (quadrants[direction] as QuadTreeNode).insert(position, value)
            } else {
                // for leaf we have to replace it with node and reinsert previous value
                val oldLeaf = (quadrants[direction] as QuadTreeLeaf)

                if (position == oldLeaf.position) {
                    throw PositionNotEmptyException()
                }

                val newNode = QuadTreeNode(quadBox)
                newNode.insert(oldLeaf.position, oldLeaf.value)
                newNode.insert(position, value)
                quadrants[direction] = newNode
            }
        } else {
            quadrants[direction] = QuadTreeLeaf(quadBox, position, value)
        }

        return true
    }

    private fun getNorthWestBox(): BoundingBox {
        return BoundingBox(box.topLeft, box.getCenter())
    }

    private fun getNorthEastBox(): BoundingBox {
        val center = box.getCenter()
        return BoundingBox(Position(center.xPos, box.topLeft.yPos), Position(box.bottomRight.xPos, center.yPos))
    }

    private fun getSouthEastBox(): BoundingBox {
        return BoundingBox(box.getCenter(), box.bottomRight)
    }

    private fun getSouthWestBox(): BoundingBox {
        val center = box.getCenter()
        return BoundingBox(Position(box.topLeft.xPos, center.yPos), Position(center.xPos, box.bottomRight.yPos))
    }
}