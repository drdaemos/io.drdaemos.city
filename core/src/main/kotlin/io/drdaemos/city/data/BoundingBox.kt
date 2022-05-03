package io.drdaemos.city.data

data class BoundingBox (val topLeft: Position, val bottomRight: Position) {
    fun contains(position: Position): Boolean {
        return topLeft.xPos <= position.xPos && bottomRight.xPos >= position.xPos
                && topLeft.yPos <= position.yPos && bottomRight.yPos >= position.yPos
    }

    fun getCenter(): Position {
        return Position((bottomRight.xPos - topLeft.xPos) / 2.0f, (bottomRight.yPos - topLeft.yPos) / 2.0f)
    }

    override fun toString(): String {
        return "$topLeft - $bottomRight"
    }
}