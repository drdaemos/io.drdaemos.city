package io.drdaemos.city.simulation.world

import io.drdaemos.city.data.Position

interface ObjectInterface {
    val position: Position

    fun print(): String
}