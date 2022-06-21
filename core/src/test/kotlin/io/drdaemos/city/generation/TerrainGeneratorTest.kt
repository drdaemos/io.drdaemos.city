package io.drdaemos.city.generation

import io.drdaemos.city.spatial.BoundingBox
import org.junit.jupiter.api.Test

internal class TerrainGeneratorTest {

    @Test
    fun shouldGenerateTerrainRegions() {
        val generator = TerrainGenerator(BoundingBox(128f, 128f), 1000L)
        val regions = generator.randomIsland(100)
        assert(regions.isNotEmpty())
    }
}