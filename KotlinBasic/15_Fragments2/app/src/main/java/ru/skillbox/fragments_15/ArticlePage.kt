package ru.skillbox.fragments_15

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticlePage(
    @StringRes val textRes: Int,
    @ColorRes val bgColorRes: Int,
    @DrawableRes val drawableRes: Int
)

/*enum class ArticlePage(
    @StringRes val textRes: Int,
    @ColorRes val bgColorRes: Int,
    @DrawableRes val drawableRes: Int
){
    SPORT(0,0,0),
    BUSINESS(0,0,0),
    HEALTH(0,0,0)
}*/
