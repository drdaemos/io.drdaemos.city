package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.OutOfBoundsException

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

    override fun insert(position: Position, value: Any): Boolean {
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

    override fun removeAt(position: Position): Boolean {
        if (!box.contains(position)) {
            return false
        }

        for ((direction, node) in quadrants) {
            if (!node.removeAt(position)) {
                continue
            } else {
                // removal successful
                if (node is QuadTreeLeaf) {
                    // drop leaf if empty
                    if (node.count() == 0) quadrants.remove(direction)
                    return true
                } else if (node is QuadTreeNode) {
                    // contract 1-child nodes
                    if (node.quadrants.count() == 1) {
                        val lastKey = node.quadrants.keys.first()
                        quadrants.replace(direction, node.quadrants[lastKey]!!)
                    }
                    return true
                }
            }
        }

        return false
    }

    override fun findValueAt(position: Position): Any? {
        if (!box.contains(position)) {
            return null
        }

        for ((_, node) in quadrants) {
            val value = node.findValueAt(position)
            if (value != null) return value else continue
        }

        return null
    }

    override fun findObjectsInside(area: BoundingBox): List<PositionedValue> {
        if (!box.intersects(area)) {
            return emptyList()
        }

        val result = mutableListOf<PositionedValue>()

        for ((_, node) in quadrants) {
            result.addAll(node.findObjectsInside(area))
        }

        return result
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
            val insertResult = quadrants[direction]!!.insert(position, value)

            // when insert fails: it means leaf is overloaded
            // we have to replace leaf with node and reinsert previous values
            if (!insertResult) {
                val oldLeaf = (quadrants[direction] as QuadTreeLeaf)

                val newNode = QuadTreeNode(quadBox)
                // reinserting existing nodes
                for (child in oldLeaf.getChildren()) {
                    newNode.insert(child.position, child.value)
                }
                newNode.insert(position, value)
                quadrants[direction] = newNode
            }
        } else {
            quadrants[direction] = QuadTreeLeaf(quadBox)
            quadrants[direction]?.insert(position, value)
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