package io.drdaemos.city.spatial.iterators

import io.drdaemos.city.spatial.QuadTreeLeaf
import io.drdaemos.city.spatial.QuadTreeNode
import io.drdaemos.city.spatial.QuadTreeNodeInterface
import kotlin.NoSuchElementException

class DepthFirstQuadTreeIterator<T>(rootNode: QuadTreeNode<T>) : Iterator<QuadTreeNodeInterface<T>> {
    var next: QuadTreeNodeInterface<T>? = null
    private val stack = IteratorStack<T>()

    init {
        if (rootNode.quadrants.isNotEmpty()) {
            val frame = stack.push(rootNode)
            next = rootNode.quadrants[frame.poll()]
        }
    }

    override fun hasNext(): Boolean = next != null

    override fun next(): QuadTreeNodeInterface<T> {
        if (!hasNext()) {
            throw NoSuchElementException()
        }

        val element = next!!
        next = findNext(element)

        return element
    }

    /**
     * Based on depth-first approach
     */
    private fun findNext(current: QuadTreeNodeInterface<T>): QuadTreeNodeInterface<T>? {
        when (current) {
            is QuadTreeNode -> {
                return if (current.quadrants.isNotEmpty()) {
                    val frame = stack.push(current)
                    current.quadrants[frame.poll()]
                } else {
                    val frame = stack.lookUp()
                    frame?.getNextQuadNode()
                }
            }
            is QuadTreeLeaf -> {
                val frame = stack.lookUp()
                return frame?.getNextQuadNode()
            }
        }

        return null
    }
}