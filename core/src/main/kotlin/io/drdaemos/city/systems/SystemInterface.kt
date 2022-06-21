package io.drdaemos.city.systems

import io.drdaemos.city.scenes.RenderingContext

interface SystemInterface {
    fun update(context: RenderingContext)
}