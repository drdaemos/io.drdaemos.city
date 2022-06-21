package io.drdaemos.city.components

import io.drdaemos.city.spatial.Position
import io.drdaemos.city.entities.EntityInterface

class Transform(override val entity: EntityInterface, var position: Position) : ComponentInterface {
}