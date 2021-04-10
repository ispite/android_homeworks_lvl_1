package ru.skillbox.codeconventions_hw_8_4

import classes.Wheel

fun main() {
    val wheel = Wheel();
    wheel.catchCheck();
    wheel.catchPressureException(-2.0);
    wheel.catchPressureException(2.0);
    wheel.catchPressureException(20.0);
    wheel.catchCheck();
}
