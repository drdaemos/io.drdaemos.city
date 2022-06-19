package io.drdaemos.city.generation.geometry

import io.drdaemos.city.data.Position
import io.drdaemos.city.data.spatial.Triangle

interface TriangulationInterface {
    fun getTriangles (points: List<Position>): Set<Triangle>
}