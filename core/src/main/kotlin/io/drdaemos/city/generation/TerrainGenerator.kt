package io.drdaemos.city.generation

import com.badlogic.gdx.graphics.Color
import io.drdaemos.city.generation.noise.SimplexNoiseGenerator
import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.data.Position
import io.drdaemos.city.data.PositionedValue
import kotlin.math.pow

const val ISLAND_SHAPING = .6f
const val NOISE_WAVELENGTH = 200
const val TEST_MAP_WIDTH = 1024
const val TEST_MAP_HEIGHT = 1024

class TerrainGenerator {
    private val simplexNoiseGenerator = SimplexNoiseGenerator(random(1000L))

    fun generate(): List<PositionedValue<Color>> {
        val list = mutableListOf<PositionedValue<Color>>()
        for (x in 0..TEST_MAP_WIDTH) {
            for (y in 0..TEST_MAP_HEIGHT) {
                val elevation = generateElevation(x.toFloat(), y.toFloat())
                val color = when {
                    elevation > .9f -> Color(255 / 255f, 255 / 255f, 255 / 255f, 1f) // snowest caps
                    elevation > .85f -> Color(200 / 255f, 200 / 255f, 210 / 255f, 1f) // snow caps
                    elevation > .8f -> Color(130 / 255f, 180 / 255f, 100 / 255f, 1f) // hills
                    elevation > .52f -> Color(99 / 255f, 147 / 255f, 77 / 255f, 1f) // plains
                    elevation > .5f -> Color(0xF1 / 255f, 0xD5 / 255f, 0xAE / 255f, 1f) // sand
                    else -> Color(28 / 255f, 104 / 255f, 155 / 255f, 1f) // water
                }
                list.add(PositionedValue(
                    Position(x.toFloat(), y.toFloat()),
                    color
                ))
            }
        }

        return list
    }

    private fun generateElevation(x: Float, y: Float): Float {
        val noise = generateNoise(x, y)
        return shapeAsIsland(x, y, noise)
    }

    private fun generateNoise(x: Float, y: Float): Float {
        return simplexNoiseGenerator.random2D(x / NOISE_WAVELENGTH, y / NOISE_WAVELENGTH)
    }

    private fun shapeAsIsland(x: Float, y: Float, elevation: Float): Float {
        val nx = 2 * x / TEST_MAP_WIDTH - 1
        val ny = 2 * y / TEST_MAP_HEIGHT - 1
        val distance = 1 - (1 - nx.pow(2)) * (1 - ny.pow(2))
        val blendingQ = ISLAND_SHAPING
        return (1 - distance) * blendingQ + elevation * (1 - blendingQ)
    }
}