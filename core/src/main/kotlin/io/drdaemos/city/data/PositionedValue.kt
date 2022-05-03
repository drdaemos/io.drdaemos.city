package io.drdaemos.city.data

data class PositionedValue (val position: Position, val value: Any) {
    override fun toString(): String {
        return "$position - $value"
    }
}