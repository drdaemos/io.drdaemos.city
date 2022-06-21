package io.drdaemos.city.entities

import io.drdaemos.city.components.ComponentInterface
import kotlin.reflect.KClass

interface EntityInterface {
    val components: Map<KClass<out ComponentInterface>, ComponentInterface>
}