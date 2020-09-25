@file:JvmName("NumberUtils")
package com.android.githubuserfinder.utils

fun roundUp(number: Int, divisor: Int): Int {
    return (number + divisor - 1) / divisor
}