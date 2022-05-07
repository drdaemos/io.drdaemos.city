package io.drdaemos.city.generation

import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.Position
import io.drdaemos.city.data.QuadTreeNode
import io.drdaemos.city.data.exceptions.PositionNotEmptyException

class RandomPointRegionGenerator(val width: Float, val height: Float, val points: Int) {
    val boundary = BoundingBox(Position(0f, 0f), Position(width, height))
    val map = QuadTreeNode(boundary)

    fun generate(): QuadTreeNode {
        for (i in 1..points) {
            try {
                map.insert(Position(random.nextFloat() * width, random.nextFloat() * height), i)
            } catch (_: PositionNotEmptyException) {
                // ignore duplicate positions
            }
        }

        return map
    }
}