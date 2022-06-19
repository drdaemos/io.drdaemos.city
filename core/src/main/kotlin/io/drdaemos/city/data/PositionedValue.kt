package io.drdaemos.city.data

data class PositionedValue<T> (val position: Position, val value: T) {
    override fun toString(): String {
        return "${position.x}:${position.y} - $value"
    }
}