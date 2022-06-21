package io.drdaemos.city.spatial

data class PositionedValue<T> (val position: Position, val value: T) {
    override fun toString(): String {
        return "${position.x}:${position.y} - $value"
    }
}