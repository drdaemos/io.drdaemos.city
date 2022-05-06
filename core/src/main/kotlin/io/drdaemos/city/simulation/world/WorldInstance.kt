package io.drdaemos.city.simulation.world

import io.drdaemos.city.data.BoundingBox
import io.drdaemos.city.data.Position
import io.drdaemos.city.data.QuadTreeNode

class WorldInstance () {
    val boundary = BoundingBox(Position(0.0f, 0.0f), Position(256.0f, 256.0f))
    val objects = QuadTreeNode(boundary)
    val roads = QuadTreeNode(boundary)
    val terrain = QuadTreeNode(boundary)
}