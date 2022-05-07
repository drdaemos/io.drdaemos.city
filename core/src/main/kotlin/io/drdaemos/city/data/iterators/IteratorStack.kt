package io.drdaemos.city.data.iterators

import io.drdaemos.city.data.QuadTreeNode
import io.drdaemos.city.data.Quadrants
import kotlin.collections.ArrayDeque

class IteratorStack {
    private val stack = ArrayDeque<StackFrame>()

    fun push(node: QuadTreeNode): StackFrame {
        val quads = node.quadrants.keys
        val queue = ArrayDeque<Quadrants>()
        queue.addAll(quads)
        stack.addLast(StackFrame(node, queue))
        return stack.last()
    }

    fun pop(): StackFrame {
        return stack.removeLast()
    }

    fun peek(): StackFrame? {
        return stack.lastOrNull()
    }

    fun lookUp(): StackFrame? {
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