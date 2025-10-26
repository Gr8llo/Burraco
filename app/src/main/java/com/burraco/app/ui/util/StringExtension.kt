package com.burraco.app.ui.util

fun String.isValidPoint(): Boolean {
    return when {
        (this.toIntOrNull() ?: 0) % 5 != 0 -> false
        (this.toIntOrNull() ?: 0) >= 1500 -> false
        (this.toIntOrNull() ?: 0) <= -1500 -> false
        this.any { !it.isDigit() && it != '-' } -> false
        else -> true
    }
}