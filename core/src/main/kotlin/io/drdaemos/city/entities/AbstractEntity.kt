package io.drdaemos.city.entities

import io.drdaemos.city.components.ComponentInterface
import kotlin.reflect.KClass

abstract class AbstractEntity : EntityInterface {
    override val components = mutableMapOf<KClass<out ComponentInterface>, ComponentInterface>()

    inline fun <reified T: ComponentInterface> get(): T {
        return components[T::class] as T
    }
}