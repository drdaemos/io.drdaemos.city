package io.drdaemos.city.simulation.world.objects

import io.drdaemos.city.simulation.world.ObjectInterface
import io.drdaemos.city.data.Position

class Building(override val position: Position) : ObjectInterface {
    override fun print(): String {
        return "🏢"
    }
}