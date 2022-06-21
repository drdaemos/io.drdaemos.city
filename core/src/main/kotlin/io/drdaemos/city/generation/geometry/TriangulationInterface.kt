package io.drdaemos.city.generation.geometry

import io.drdaemos.city.spatial.Position
import io.drdaemos.city.spatial.aliases.Triangle

interface TriangulationInterface {
    fun getTriangles (points: List<Position>): Set<Triangle>
}