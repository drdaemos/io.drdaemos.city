package io.drdaemos.city.generation.noise

interface SimplexNoiseGeneratorInterface {
    /** Generate 2D noise with the origin [x],[y] */
    fun random2D(x: Float, y: Float): Float
    /** Generate 3D noise with the origin [x],[y],[z] */
    fun random3D(x: Float, y: Float, z: Float): Float
    /** Generate 4D noise with the origin [x],[y],[z],[w] */
    fun random4D(x: Float, y: Float, z: Float, w: Float): Float
}