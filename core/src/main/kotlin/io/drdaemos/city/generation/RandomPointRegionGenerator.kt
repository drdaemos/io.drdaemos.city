package io.drdaemos.city.generation

import com.badlogic.gdx.math.MathUtils.random
import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.Position
import io.drdaemos.city.data.QuadTreeNode

class RandomPointRegionGenerator(val width: Float, val height: Float, val points: Int) {
    val boundary = BoundingBox(Position(0f, 0f), Position(width, height))
    val map = QuadTreeNode(boundary)

    fun generate(): QuadTreeNode {
        for (i in 1..points) {
            map.insert(Position(random.nextFloat() * width, random.nextFloat() * height), i)
        }

        return map
    }
}