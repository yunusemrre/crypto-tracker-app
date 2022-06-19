package com.gp.cryptotrackerapp.util.extension

fun Double.round2DecimalVol(): String {
    return String.format("%.3f", this / 10000000000)
}

fun Double.round3Decimal(): String{
    return String.format("%.3f",this)
}

fun Float.round3Decimal(): String {
    return String.format("%.3f", this);
}