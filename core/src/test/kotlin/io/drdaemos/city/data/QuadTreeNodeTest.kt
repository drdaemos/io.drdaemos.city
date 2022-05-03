package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.PositionNotEmptyException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuadTreeNodeTest {

    @Test
    fun shouldAddNodeToEmptyQuadTree() {
        // Creating 3:3 quad tree
        val tree = QuadTreeNode(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(1.0f, 1.0f), "obj 1")

        val value = tree.findValueAt(Position(1.0f, 1.0f))
        assertEquals(value, "obj 1")
    }

    @Test
    fun shouldNotAddNodeToSamePosition() {
        // Creating 3:3 quad tree
        val tree = QuadTreeNode(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(1.0f, 1.0f), "obj 1")
        assertThrows<PositionNotEmptyException> {
            tree.insert(Position(1.0f, 1.0f), "Same position Obj")
        }

        val value = tree.findValueAt(Position(1.0f, 1.0f))
        assertEquals(value, "obj 1")
    }

    @Test
    fun shouldAddMultipleNodesToQuadTree() {
        /* setup 3:3 quad tree
        | 0:0 |         | 2:0 |
        |     | 1.5:1.5 |     |
        | 0:2 |         | new |
         */

        val tree = QuadTreeNode(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.5f, 1.5f),"1.5:1.5")
        tree.insert(Position(2.0f, 0.0f),"2:0")

        // test
        tree.insert(Position(2.0f, 2.0f), "new obj")

        val value = tree.findValueAt(Position(2.0f, 2.0f))
        assertEquals(value, "new obj")
    }

    @Test
    fun shouldRemoveNodeFromTree() {
        /* setup 3:3 quad tree
        | 0:0 |         | 2:0 |
        |     | 1.5:1.5 |     |
        | 0:2 |         |     |
         */

        val tree = QuadTreeNode(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.5f, 1.5f),"1.5:1.5")
        tree.insert(Position(2.0f, 0.0f),"2:0")

        // test
        assertTrue(tree.removeAt(Position(1.5f, 1.5f)))
        assertNull(tree.findValueAt(Position(1.5f, 1.5f)))
    }
}