package io.drdaemos.city.generation.noise

class SimplexNoiseGenerator(private val seed: Long = 0L): SimplexNoiseGeneratorInterface {

    override fun random2D(x: Float, y: Float): Float {
        return normalize(OpenSimplex2S.noise2(seed, x.toDouble(), y.toDouble()), -1f, 1f)
    }

    override fun random3D(x: Float, y: Float, z: Float): Float {
        return normalize(OpenSimplex2S.noise3_ImproveXY(seed, x.toDouble(), y.toDouble(), z.toDouble()), -1f, 1f)
    }

    override fun random4D(x: Float, y: Float, z: Float, w: Float): Float {
        return normalize(OpenSimplex2S.noise4_ImproveXYZ(seed, x.toDouble(), y.toDouble(), z.toDouble(), w.toDouble()), -1f, 1f)
    }

    private fun normalize(value: Float, min: Float, max: Float): Float {
        return (value - min) / (max - min)
    }
}