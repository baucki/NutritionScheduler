package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments.AccountFragment
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments.HomeFragment
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments.SearchFragment
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments.StatisticsFragment

class PagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 5
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
        const val FRAGMENT_4 = 3
        const val FRAGMENT_5 = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FRAGMENT_1 -> HomeFragment()
            FRAGMENT_2 -> SearchFragment()
            FRAGMENT_3 -> StatisticsFragment()
            FRAGMENT_4 -> Fragment()
            else -> AccountFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

}