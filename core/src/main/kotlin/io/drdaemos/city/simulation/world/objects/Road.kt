package io.drdaemos.city.simulation.world.objects

import io.drdaemos.city.data.Position
import io.drdaemos.city.simulation.world.ObjectInterface

class Road(override val position: Position) : ObjectInterface {
    override fun print(): String {
        return "🛣️"
    }
}