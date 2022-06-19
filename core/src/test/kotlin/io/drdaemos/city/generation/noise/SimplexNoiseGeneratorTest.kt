package io.drdaemos.city.generation.noise

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SimplexNoiseGeneratorTest {

    @Test
    fun valueStaysWithinRange() {
        val generator = SimplexNoiseGenerator()

        val values = mutableListOf<Float>()
        for (x in 1..1000) {
            for (y in 1..1000) {
                values.add(generator.random2D(x.toFloat(), y.toFloat()))
            }
        }

        // test
        for (value in values) {
            assert(value in 0f..1f)
        }
    }

    @Test
    fun sameSeedProducesSameValues() {
        val seed = 1000L
        val generator1 = SimplexNoiseGenerator(seed)

        val values1 = mutableListOf<Float>()
        for (x in 1..1) {
            for (y in 1..5) {
                values1.add(generator1.random2D(x.toFloat(), y.toFloat()))
            }
        }

        val generator2 = SimplexNoiseGenerator(seed)
        val values2 = mutableListOf<Float>()
        for (x in 1..1) {
            for (y in 1..5) {
                values2.add(generator2.random2D(x.toFloat(), y.toFloat()))
            }
        }

        // test
        assertArrayEquals(values1.toFloatArray(), values2.toFloatArray())
    }

    @Test
    fun differentSeedProducesSameValues() {
        val seed1 = 1000L
        val generator1 = SimplexNoiseGenerator(seed1)

        val values1 = mutableListOf<Float>()
        for (x in 1..1) {
            for (y in 1..5) {
                values1.add(generator1.random2D(x.toFloat(), y.toFloat()))
            }
        }

        val seed2 = 999L
        val generator2 = SimplexNoiseGenerator(seed2)
        val values2 = mutableListOf<Float>()
        for (x in 1..1) {
            for (y in 1..5) {
                values2.add(generator2.random2D(x.toFloat(), y.toFloat()))
            }
        }

        // test
        assertFalse(values1.toFloatArray().contentEquals(values2.toFloatArray()))
    }
}