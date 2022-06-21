package io.drdaemos.city.generation

import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.generation.noise.RandomPointsGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RandomPointsGeneratorTest {

    @Test
    fun shouldGeneratePoints() {
        val generator = RandomPointsGenerator(BoundingBox(128f, 128f), 1000L);
        val list = generator.randomPoints(50)

        assert(list.isNotEmpty())
        assertEquals(50, list.count())
    }
}