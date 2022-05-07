package io.drdaemos.city.data.iterators

import io.drdaemos.city.data.QuadTreeNode
import io.drdaemos.city.data.QuadTreeNodeInterface
import io.drdaemos.city.data.Quadrants
import kotlin.collections.ArrayDeque

data class StackFrame(val node: QuadTreeNode, val queue: ArrayDeque<Quadrants>) {
    fun poll(): Quadrants {
        return queue.removeFirst()
    }

    fun getNextQuadNode(): QuadTreeNodeInterface? {
        return node.quadrants[poll()]
    }

    fun hasNextQuadrant(): Boolean {
        return !queue.isEmpty()
    }
}