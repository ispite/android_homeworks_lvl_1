package ru.skillbox.fragments_15

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ArticlePage(
    @DrawableRes val articleImage: Int,
    @StringRes val articleCaption: Int,
    @StringRes val articleText: Int,
    var articleType: ArticleTypes
)

enum class ArticleTypes {
    SPORT,
    BUSINESS,
    HEALTH,
    EVERYTHING
}
