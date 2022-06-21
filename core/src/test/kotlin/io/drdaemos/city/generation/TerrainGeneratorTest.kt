package io.drdaemos.city.generation

import org.junit.jupiter.api.Test

internal class TerrainGeneratorTest {

    @Test
    fun shouldGenerateRegions() {
        val generator = TerrainGenerator()
        val regions = generator.generate()
        assert(regions.isNotEmpty())
    }
}