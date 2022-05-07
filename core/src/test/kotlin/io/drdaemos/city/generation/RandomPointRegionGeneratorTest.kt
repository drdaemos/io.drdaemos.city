package io.drdaemos.city.generation

import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.Position
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RandomPointRegionGeneratorTest {

    @Test
    fun generate() {
        val generator = RandomPointRegionGenerator(128f, 128f, 50);
        val tree = generator.generate()
        val points = tree.findObjectsInside(
            BoundingBox(Position(0f, 0f), Position(128f, 128f))
        )
    }
}