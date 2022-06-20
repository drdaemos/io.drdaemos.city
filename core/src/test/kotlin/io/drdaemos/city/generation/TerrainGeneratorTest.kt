package io.drdaemos.city.generation

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TerrainGeneratorTest {

    @Test
    fun shouldGenerateRegions() {
        val generator = TerrainGenerator()
        val terrain = generator.generateRegions()
        assert(terrain.regions.isNotEmpty())
    }
}