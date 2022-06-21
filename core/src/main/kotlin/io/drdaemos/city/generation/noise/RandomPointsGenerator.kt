package io.drdaemos.city.generation.noise

import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.spatial.Position

class RandomPointsGenerator(private val boundary: BoundingBox, private val seed: Long = 0L) {
    fun randomPoints(count: Int): List<Position> {
        random.setSeed(seed)
        val points = mutableListOf<Position>()

        repeat(count) {
            points.add(getNextPoint())
        }

        return points
    }

    private fun getNextPoint(): Position {
        val width = boundary.getWidth()
        val height = boundary.getHeight()
        val offsetX = boundary.topLeft.x
        val offsetY = boundary.topLeft.y

        return Position(
            vX = (random.nextFloat() * width + offsetX),
            vY = (random.nextFloat() * height + offsetY)
        )
    }
}