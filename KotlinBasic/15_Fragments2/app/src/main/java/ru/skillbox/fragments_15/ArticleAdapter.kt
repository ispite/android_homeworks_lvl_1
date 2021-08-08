package ru.skillbox.fragments_15

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticleAdapter(
    private val articles: Array<ArticlePage>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun createFragment(position: Int): Fragment {
        val currentArticlePage: ArticlePage = articles[position]
        return ArticleFragment.newInstance(
            currentArticlePage.articleImage,
            currentArticlePage.articleCaption,
            currentArticlePage.articleText,
            currentArticlePage.articleType
        )
    }
}