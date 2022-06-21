package io.drdaemos.city.spatial.calculation

import io.drdaemos.city.spatial.PositionedValue

data class NearestObjectState<T>(var best: PositionedValue<T>? = null, var distance: Float? = null)