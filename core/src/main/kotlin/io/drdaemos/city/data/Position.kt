package io.drdaemos.city.data

data class Position (val xPos: Float, val yPos: Float) {
    override fun equals(other: Any?): Boolean {
        return other is Position && xPos == other.xPos && yPos == other.yPos
    }

    override fun hashCode(): Int {
        var result = xPos.hashCode()
        result = 31 * result + yPos.hashCode()
        return result
    }

    override fun toString(): String {
        return "$xPos:$yPos"
    }
}