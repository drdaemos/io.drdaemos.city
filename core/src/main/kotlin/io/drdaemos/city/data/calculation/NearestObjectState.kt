package io.drdaemos.city.data.calculation

import io.drdaemos.city.data.PositionedValue

data class NearestObjectState<T>(var best: PositionedValue<T>? = null, var distance: Float? = null)