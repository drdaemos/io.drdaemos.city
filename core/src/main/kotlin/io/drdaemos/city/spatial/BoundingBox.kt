package io.drdaemos.city.spatial

data class BoundingBox (val topLeft: Position, val bottomRight: Position) {
    constructor(width: Float, height: Float) : this(Position(0f, 0f), Position(width, height))

    fun contains(position: Position): Boolean {
        return topLeft.x <= position.x && bottomRight.x >= position.x
                && topLeft.y <= position.y && bottomRight.y >= position.y
    }

    fun intersects(other: BoundingBox): Boolean {
        if (topLeft.x == bottomRight.x
            || topLeft.y == bottomRight.y
            || other.topLeft.x == other.bottomRight.x
            || other.topLeft.y == other.bottomRight.y) {
            // the line cannot have positive overlap
            return false
        }

        // If one rectangle is on left side of other
        if (topLeft.x >= other.bottomRight.x || other.topLeft.x >= bottomRight.x)
            return false

        // If one rectangle is above other
        if (bottomRight.y <= other.topLeft.y || other.bottomRight.y <= topLeft.y)
            return false

        return true
    }

    fun getCenter(): Position {
        return Position(topLeft.x + (bottomRight.x - topLeft.x) / 2.0f, topLeft.y + (bottomRight.y - topLeft.y) / 2.0f)
    }

    fun getWidth(): Float {
        return bottomRight.x - topLeft.x
    }

    fun getHeight(): Float {
        return bottomRight.y - topLeft.y
    }

    override fun toString(): String {
        return "${topLeft.x}:${topLeft.y} - ${bottomRight.x}:${bottomRight.y}"
    }
}