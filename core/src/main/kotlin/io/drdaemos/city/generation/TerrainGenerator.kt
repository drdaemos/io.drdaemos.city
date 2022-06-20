package io.drdaemos.city.generation

import com.badlogic.gdx.graphics.Color
import io.drdaemos.city.generation.noise.SimplexNoiseGenerator
import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.Position
import io.drdaemos.city.data.PositionedValue
import io.drdaemos.city.data.TerrainType
import io.drdaemos.city.generation.geometry.VoronoiDiagram
import io.drdaemos.city.generation.noise.BlueNoiseGenerator
import io.drdaemos.city.simulation.world.Terrain
import io.drdaemos.city.simulation.world.TerrainRegion
import kotlin.math.pow

const val ISLAND_SHAPING = .6f
const val NOISE_WAVELENGTH = 200
const val TEST_MAP_WIDTH = 1024f
const val TEST_MAP_HEIGHT = 1024f
const val INITIAL_POINTS = 200

class TerrainGenerator (seed: Long = random(1000L)) {
    private val box = BoundingBox(TEST_MAP_WIDTH, TEST_MAP_HEIGHT)
    private val simplexNoiseGenerator = SimplexNoiseGenerator(seed)
    private val blueNoiseGenerator = BlueNoiseGenerator(box, seed)
    private val voronoiDiagram = VoronoiDiagram()

    fun generateRegions(): Terrain {
        val terrain = Terrain(box)
        val randomPoints = blueNoiseGenerator.randomList(INITIAL_POINTS)
        val polygons = voronoiDiagram.getPolygons(randomPoints)
        val regions = mutableListOf<TerrainRegion>()

        for (poly in polygons) {
            val elevation = generateElevation(poly.center.x, poly.center.y)
            val type = mapElevationToType(elevation)
            regions.add(TerrainRegion(poly, type))
        }

        terrain.regions = regions

        return terrain
    }

    private fun mapElevationToType(elevation: Float): TerrainType {
        return when {
            elevation > .8f -> TerrainType.Hills
            elevation > .52f -> TerrainType.Plains
            elevation > .5f -> TerrainType.Sand
            else -> TerrainType.Water
        }
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