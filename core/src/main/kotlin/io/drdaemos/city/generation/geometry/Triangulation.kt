package io.drdaemos.city.generation.geometry

import com.github.polyrocketmatt.game.math.geometry.voronoi.Delaunay
import io.drdaemos.city.spatial.Position
import io.drdaemos.city.spatial.aliases.Triangle

class Triangulation : TriangulationInterface {
    private lateinit var delaunator: Delaunay<Position>

    override fun getTriangles (points: List<Position>): Set<Triangle> {
        delaunator = Delaunay(points, Delaunay.DelaunayAlgorithm.DELAUNATOR)
        return delaunator.triangles()
    }
}