package io.drdaemos.city.generation.noise

import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.spatial.BoundingBox
import io.drdaemos.city.spatial.Position
import io.drdaemos.city.spatial.QuadTreeNode
import io.drdaemos.city.spatial.exceptions.PositionNotEmptyException

class BlueNoiseGenerator (private val boundary: BoundingBox, private val seed: Long = 0L) {
    fun randomPoints(count: Int, randomness: Int = 10): List<Position> {
        random.setSeed(seed)
        val tree = QuadTreeNode<Any?>(boundary)
        val points = mutableListOf<Position>()

        repeat(count) {
            val candidatesCount = (points.count() / randomness) + 1

            val candidates = mutableListOf<Position>()
            repeat(candidatesCount) {
                candidates.add(getNextPoint())
            }

            val best = candidates.maxByOrNull {
                val closest = tree.findNearestObject(it)
                closest?.position?.dist(it) ?: 1f
            }

            if (best != null) {
                try {
                    tree.insert(best, null)
                } catch (_: PositionNotEmptyException) {
                }
                points.add(best)
            }
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