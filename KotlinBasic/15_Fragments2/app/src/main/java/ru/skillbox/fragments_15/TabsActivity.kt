package ru.skillbox.fragments_15

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_tabs.*

class TabsActivity : AppCompatActivity(R.layout.activity_tabs) {

    private val screens: List<ArticlePage> = listOf(
        ArticlePage(
            textRes = R.string.onboarding_text_1,
            bgColorRes = R.color.onboarding_color_1,
            drawableRes = R.drawable.business_1
        ),
        ArticlePage(
            textRes = R.string.onboarding_text_2,
            bgColorRes = R.color.onboarding_color_2,
            drawableRes = R.drawable.business_2
        ),
        ArticlePage(
            textRes = R.string.onboarding_text_3,
            bgColorRes = R.color.onboarding_color_3,
            drawableRes = R.drawable.sport_1
        ),
        ArticlePage(
            textRes = R.string.onboarding_text_4,
            bgColorRes = R.color.onboarding_color_4,
            drawableRes = R.drawable.sport_2
        ),
        ArticlePage(
            textRes = R.string.onboarding_text_5,
            bgColorRes = R.color.onboarding_color_5,
            drawableRes = R.drawable.health_1
        ),
        ArticlePage(
            textRes = R.string.onboarding_text_6,
            bgColorRes = R.color.onboarding_color_6,
            drawableRes = R.drawable.health_2
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = OnBoardingAdapter(screens, this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            if (position % 2 == 0)
                tab.setIcon(R.drawable.ic_baseline_run)
        }.attach()

        tabLayout.getTabAt(1)?.orCreateBadge?.apply {
            number = 2
            badgeGravity = BadgeDrawable.TOP_END
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.removeBadge()
            }
        })
        showFilterDialog.setOnClickListener {
            showDialogFragment()
        }
    }

    private fun showDialogFragment() {
        FilterDialogFragment()
            .show(supportFragmentManager, "FILTER")
    }

    fun doAnything() {
        toast("dfgdfgdf = edfgdfg}")
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}