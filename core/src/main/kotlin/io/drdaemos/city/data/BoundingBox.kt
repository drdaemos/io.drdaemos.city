package io.drdaemos.city.data

data class BoundingBox (val topLeft: Position, val bottomRight: Position) {
    fun contains(position: Position): Boolean {
        return topLeft.xPos <= position.xPos && bottomRight.xPos >= position.xPos
                && topLeft.yPos <= position.yPos && bottomRight.yPos >= position.yPos
    }

    fun intersects(other: BoundingBox): Boolean {
        if (topLeft.xPos == bottomRight.xPos
            || topLeft.yPos == bottomRight.yPos
            || other.topLeft.xPos == other.bottomRight.xPos
            || other.topLeft.yPos == other.bottomRight.yPos) {
            // the line cannot have positive overlap
            return false
        }

        // If one rectangle is on left side of other
        if (topLeft.xPos >= other.bottomRight.xPos || other.topLeft.xPos >= bottomRight.xPos)
            return false

        // If one rectangle is above other
        if (bottomRight.yPos <= other.topLeft.yPos || other.bottomRight.yPos <= topLeft.yPos)
            return false

        return true
    }

    fun getCenter(): Position {
        return Position(topLeft.xPos + (bottomRight.xPos - topLeft.xPos) / 2.0f, topLeft.yPos + (bottomRight.yPos - topLeft.yPos) / 2.0f)
    }

    fun getWidth(): Float {
        return bottomRight.xPos - topLeft.xPos
    }

    fun getHeight(): Float {
        return bottomRight.yPos - topLeft.yPos
    }

    override fun toString(): String {
        return "$topLeft - $bottomRight"
    }
}