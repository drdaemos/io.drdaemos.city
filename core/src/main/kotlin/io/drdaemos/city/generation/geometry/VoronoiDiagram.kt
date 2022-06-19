package io.drdaemos.city.generation.geometry

import com.github.polyrocketmatt.game.math.geometry.shape.ConvexPolygon
import com.github.polyrocketmatt.game.math.geometry.voronoi.Voronoi
import io.drdaemos.city.data.Position

class VoronoiDiagram {
    private lateinit var voronoi: Voronoi<Position>

    fun getPolygons (points: List<Position>): Set<ConvexPolygon> {
        voronoi = Voronoi(points, Voronoi.VoronoiAlgorithm.DELAUNATOR)
        return voronoi.cells()
    }
}