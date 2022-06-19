package io.drdaemos.city.data

import io.drdaemos.city.data.exceptions.PositionNotEmptyException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuadTreeNodeTest {

    @Test
    fun shouldAddNodeToEmptyQuadTree() {
        // Creating 3:3 quad tree
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(1.0f, 1.0f), "obj 1")

        val value = tree.findValueAt(Position(1.0f, 1.0f))
        assertEquals(value, "obj 1")
    }

    @Test
    fun shouldNotAddNodeToSamePosition() {
        // Creating 3:3 quad tree
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
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
        | 6 dots |         | 2:0 |
        |        | 1.5:1.5 |     |
        | 0:2    |         | new |
         */
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.1f, 0.1f),"0.1:0.1")
        tree.insert(Position(0.2f, 0.2f),"0.2:0.2")
        tree.insert(Position(0.3f, 0.3f),"0.3:0.3")
        tree.insert(Position(0.4f, 0.4f),"0.4:0.4")
        tree.insert(Position(0.5f, 0.5f),"0.5:0.5")
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
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.5f, 1.5f),"1.5:1.5")
        tree.insert(Position(2.0f, 0.0f),"2:0")

        // test
        assertTrue(tree.removeAt(Position(1.5f, 1.5f)))
        assertNull(tree.findValueAt(Position(1.5f, 1.5f)))
    }

    @Test
    fun shouldFindNodesInArea() {
        /* setup 3:3 quad tree
        | 0:0 |         | 2:0 |
        |     | 1:1     |     |
        | 0:2 |         |     |
         */
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.0f, 1.0f),"1:1")
        tree.insert(Position(2.0f, 0.0f),"2:0")

        val expected = listOf(
            PositionedValue(Position(0.0f, 2.0f),"0:2"),
            PositionedValue(Position(1.0f, 1.0f),"1:1")
        )

        // test
        val actual = tree.findObjectsInside(BoundingBox(Position(0.0f, 1.0f), Position(1.5f, 2.1f)))
        assertTrue(actual.size == expected.size && actual.containsAll(expected), "$actual")
    }

    @Test
    fun shouldIterateOverNodes() {
        // setup
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.1f, 0.1f),"0.1:0.1")
        tree.insert(Position(0.2f, 0.2f),"0.2:0.2")
        tree.insert(Position(0.3f, 0.3f),"0.3:0.3")
        tree.insert(Position(0.4f, 0.4f),"0.4:0.4")
        tree.insert(Position(0.5f, 0.5f),"0.5:0.5")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.5f, 1.5f),"1.5:1.5")
        tree.insert(Position(1.6f, 1.2f),"1.6:1.2")
        tree.insert(Position(1.9f, 1.5f),"1.9:1.5")
        tree.insert(Position(1.5f, 1.4f),"1.5:1.4")
        tree.insert(Position(2f, 0f),"2:0")
        tree.insert(Position(2.5f, 0f),"2.5:0")
        tree.insert(Position(2.5f, .1f),"2.5:0.1")
        tree.insert(Position(2.5f, .2f),"2.5:0.2")
        tree.insert(Position(2.5f, 1f),"2.5:1")
        tree.insert(Position(3f, 3f),"3:3")

        // test
        val iterator = tree.iterator()
        assertEquals("Node: 0.0:0.0 - 1.5:1.5, 2", iterator.next().toString())
        assertEquals("Node: 0.0:0.0 - 0.75:0.75, 2", iterator.next().toString())
        assertEquals("Leaf: 0.0:0.0 - 0.375:0.375, 4", iterator.next().toString())
        assertEquals("Leaf: 0.375:0.375 - 0.75:0.75, 2", iterator.next().toString())
        assertEquals("Leaf: 0.75:0.75 - 1.5:1.5, 2", iterator.next().toString())
        assertEquals("Leaf: 0.0:1.5 - 1.5:3.0, 1", iterator.next().toString())
        assertEquals("Node: 1.5:0.0 - 3.0:1.5, 4", iterator.next().toString())
        assertEquals("Leaf: 1.5:0.75 - 2.25:1.5, 2", iterator.next().toString())
        assertEquals("Leaf: 1.5:0.0 - 2.25:0.75, 1", iterator.next().toString())
        assertEquals("Leaf: 2.25:0.0 - 3.0:0.75, 3", iterator.next().toString())
        assertEquals("Leaf: 2.25:0.75 - 3.0:1.5, 1", iterator.next().toString())
        assertEquals("Leaf: 1.5:1.5 - 3.0:3.0, 1", iterator.next().toString())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun shouldFindNearestObject() {
        // setup
        val tree = QuadTreeNode<String>(BoundingBox(Position(0.0f, 0.0f), Position(3.0f, 3.0f)))
        tree.insert(Position(0.0f, 0.0f),"0:0")
        tree.insert(Position(0.1f, 0.1f),"0.1:0.1")
        tree.insert(Position(0.2f, 0.2f),"0.2:0.2")
        tree.insert(Position(0.3f, 0.3f),"0.3:0.3")
        tree.insert(Position(0.4f, 0.4f),"0.4:0.4")
        tree.insert(Position(0.5f, 0.5f),"0.5:0.5")
        tree.insert(Position(0.0f, 2.0f),"0:2")
        tree.insert(Position(1.5f, 1.5f),"1.5:1.5")
        tree.insert(Position(1.6f, 1.2f),"1.6:1.2")
        tree.insert(Position(1.9f, 1.5f),"1.9:1.5")
        tree.insert(Position(1.5f, 1.4f),"1.5:1.4")
        tree.insert(Position(2f, 0f),"2:0")
        tree.insert(Position(2.5f, 0f),"2.5:0")
        tree.insert(Position(2.5f, .1f),"2.5:0.1")
        tree.insert(Position(2.5f, .2f),"2.5:0.2")
        tree.insert(Position(2.5f, 1f),"2.5:1")
        tree.insert(Position(3f, 3f),"3:3")

        //test
        val expected1 = PositionedValue(Position(0.5f, 0.5f),"0.5:0.5")
        val actual1 = tree.findNearestObject(Position(0.51f, 0.51f))
        assertEquals(expected1, actual1)

        val expected2 = PositionedValue(Position(1.9f, 1.5f),"1.9:1.5")
        val actual2 = tree.findNearestObject(Position(2f, 2f))
        assertEquals(expected2, actual2)
    }
}