package io.drdaemos.city.spatial.iterators

import io.drdaemos.city.spatial.QuadTreeNode
import io.drdaemos.city.spatial.QuadTreeNodeInterface
import io.drdaemos.city.spatial.Quadrants
import kotlin.collections.ArrayDeque

data class StackFrame<T>(val node: QuadTreeNode<T>, val queue: ArrayDeque<Quadrants>) {
    fun poll(): Quadrants {
        return queue.removeFirst()
    }

    fun getNextQuadNode(): QuadTreeNodeInterface<T>? {
        return node.quadrants[poll()]
    }

    fun hasNextQuadrant(): Boolean {
        return !queue.isEmpty()
    }
}