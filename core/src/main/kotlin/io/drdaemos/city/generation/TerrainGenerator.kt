package io.drdaemos.city.generation

import io.drdaemos.city.generation.noise.SimplexNoiseGenerator
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.data.terrain.TerrainType
import io.drdaemos.city.generation.geometry.VoronoiDiagram
import io.drdaemos.city.generation.noise.BlueNoiseGenerator
import io.drdaemos.city.data.terrain.TerrainRegion
import kotlin.math.pow

const val ISLAND_SHAPING = .6f
const val NOISE_WAVELENGTH = 200

class TerrainGenerator (private val box: BoundingBox, seed: Long = 0L) {
    private val simplexNoiseGenerator = SimplexNoiseGenerator(seed)
    private val blueNoiseGenerator = BlueNoiseGenerator(box, seed)
    private val voronoiDiagram = VoronoiDiagram()

    fun randomIsland(points: Int): List<TerrainRegion> {
        val randomPoints = blueNoiseGenerator.randomPoints(points, points / 10)
        val polygons = voronoiDiagram.getPolygons(randomPoints)
        val regions = mutableListOf<TerrainRegion>()

        for (poly in polygons) {
            val elevation = generateElevation(poly.center.x, poly.center.y)
            val type = mapElevationToType(elevation)
            regions.add(TerrainRegion(poly, type))
        }

        return regions
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
        val nx = 2 * x / box.getWidth() - 1
        val ny = 2 * y / box.getHeight() - 1
        val distance = 1 - (1 - nx.pow(2)) * (1 - ny.pow(2))
        val blendingQ = ISLAND_SHAPING
        return (1 - distance) * blendingQ + elevation * (1 - blendingQ)
    }
}