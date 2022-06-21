package io.drdaemos.city.spatial

import io.drdaemos.city.spatial.calculation.NearestObjectState
import io.drdaemos.city.spatial.exceptions.OutOfBoundsException
import io.drdaemos.city.spatial.iterators.DepthFirstQuadTreeIterator

/**
 * Quad Tree is a spatial 2d structure with 0:0 point in top-left corner
 *
 *   0 1 2 . x        NW | NE
 * 0 . . . .          ---|---
 * 1 . . o .          SW | SE
 * 2 . . . .
 * . . o . .
 * y
 */
open class QuadTreeNode<T> (val box: BoundingBox) : QuadTreeNodeInterface<T>, Iterable<QuadTreeNodeInterface<T>> {

    val quadrants: MutableMap<Quadrants, QuadTreeNodeInterface<T>> = mutableMapOf()

    override fun insert(position: Position, value: T): Boolean {
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

    override fun findValueAt(position: Position): T? {
        if (!box.contains(position)) {
            return null
        }

        for ((_, node) in quadrants) {
            val value = node.findValueAt(position)
            if (value != null) return value else continue
        }

        return null
    }

    override fun findObjectsInside(area: BoundingBox): List<PositionedValue<T>> {
        if (!box.intersects(area)) {
            return emptyList()
        }

        val result = mutableListOf<PositionedValue<T>>()

        for ((_, node) in quadrants) {
            result.addAll(node.findObjectsInside(area))
        }

        return result
    }

    override fun findNearestObject(position: Position): PositionedValue<T>? {
        val innerState = NearestObjectState<T>()
        return recursiveNearest(position, innerState)
    }

    private fun recursiveNearest(position: Position, state: NearestObjectState<T>): PositionedValue<T>? {
        if (state.best != null && state.distance != null) {
            // exclude this if point is farther away than best distance in either axis
            if (position.x < box.topLeft.x - state.distance!!
                || position.x > box.bottomRight.x + state.distance!!
                || position.y < box.topLeft.y - state.distance!!
                || position.y > box.bottomRight.y + state.distance!!) {
                return state.best
            }
        }

        // check if kid is on the right or left, and top or bottom
        // and then recurse on most likely kids first, so we quickly find a
        // nearby point and then exclude many larger rectangles later
        val rl = if (2 * position.x > box.topLeft.x + box.bottomRight.x) 1 else 0
        val bt = if (2 * position.y > box.topLeft.y + box.bottomRight.y) 1 else 0

        val quadsToVisit = listOf(
            getQuadrantByCode(bt * 2 + rl),
            getQuadrantByCode(bt * 2 + (1 - rl)),
            getQuadrantByCode((1 - bt) * 2 + rl),
            getQuadrantByCode((1 - bt) * 2 + (1 - rl))
        )

        for (direction in quadsToVisit) {
            val quad = quadrants[direction]
            if (quad != null) {
                val candidate = when (quad) {
                    is QuadTreeNode -> quad.recursiveNearest(position, state)
                    else -> quad.findNearestObject(position)
                } ?: continue

                val dist = candidate.position.dist(position)

                if (state.distance == null || dist < state.distance!!) {
                    state.best = candidate
                    state.distance = position.dist(candidate.position)
                }
            }
        }

        return state.best
    }

    private fun getQuadrantByCode(code: Int): Quadrants {
        return when (code) {
            0 -> Quadrants.NorthWest
            1 -> Quadrants.NorthEast
            2 -> Quadrants.SouthWest
            3 -> Quadrants.SouthEast
            else -> throw Error("Unexpected code")
        }
    }

    private fun tryInsertInto(direction: Quadrants, position: Position, value: T): Boolean {
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

                val newNode = QuadTreeNode<T>(quadBox)
                // reinserting existing nodes
                for (child in oldLeaf.children) {
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
        return BoundingBox(Position(center.x, box.topLeft.y), Position(box.bottomRight.x, center.y))
    }

    private fun getSouthEastBox(): BoundingBox {
        return BoundingBox(box.getCenter(), box.bottomRight)
    }

    private fun getSouthWestBox(): BoundingBox {
        val center = box.getCenter()
        return BoundingBox(Position(box.topLeft.x, center.y), Position(center.x, box.bottomRight.y))
    }

    override fun iterator(): Iterator<QuadTreeNodeInterface<T>> {
        return DepthFirstQuadTreeIterator(this)
    }

    override fun toString(): String {
        return "Node: $box, ${quadrants.count()}"
    }
}