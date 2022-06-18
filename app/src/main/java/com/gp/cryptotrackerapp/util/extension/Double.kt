package com.gp.cryptotrackerapp.util.extension

import kotlin.math.roundToInt

fun Double.round2Decimal(): String {
    return String.format("%.3f", this/10000000000)
}

fun Double.round3Decimal(): Double {
    return (this * 100.0).roundToInt() / 1000.0
}