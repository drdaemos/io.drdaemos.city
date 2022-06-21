package io.drdaemos.city.spatial.iterators

import io.drdaemos.city.spatial.QuadTreeNode
import io.drdaemos.city.spatial.Quadrants
import kotlin.collections.ArrayDeque

class IteratorStack<T> {
    private val stack = ArrayDeque<StackFrame<T>>()

    fun push(node: QuadTreeNode<T>): StackFrame<T> {
        val quads = node.quadrants.keys
        val queue = ArrayDeque<Quadrants>()
        queue.addAll(quads)
        stack.addLast(StackFrame(node, queue))
        return stack.last()
    }

    fun pop(): StackFrame<T> {
        return stack.removeLast()
    }

    fun peek(): StackFrame<T>? {
        return stack.lastOrNull()
    }

    fun lookUp(): StackFrame<T>? {
        while (true) {
            val frame = peek()
            if (frame != null) {
                if (frame.hasNextQuadrant()) {
                    return frame
                } else {
                    pop()
                }
            } else {
                return null
            }
        }
    }
}