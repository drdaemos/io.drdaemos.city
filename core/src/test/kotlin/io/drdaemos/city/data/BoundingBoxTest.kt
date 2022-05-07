package io.drdaemos.city.data

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class BoundingBoxTest {
    private val box = BoundingBox(
        Position(100f, 100f),
        Position(200f, 300f)
    )

    @BeforeEach
    internal fun setUp() {
    }

    @Test
    fun contains() {
        val insideBox = Position(101f, 101f)
        val outsideTop = Position(101f, 99f)
        val outsideBottom = Position(150f, 301f)
        assertTrue(box.contains(insideBox))
        assertFalse(box.contains(outsideTop))
        assertFalse(box.contains(outsideBottom))
    }

    @Test
    fun getCenter() {
        assertEquals(Position(150f, 200f), box.getCenter())
    }
}