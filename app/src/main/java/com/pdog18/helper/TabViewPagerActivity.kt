package com.pdog18.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.activity_tab_viewpager.*

import androidx.annotation.LayoutRes

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Layout(@LayoutRes val layoutId: Int)


abstract class TabViewPagerActivity : AppCompatActivity() {

    abstract val fragments: Array<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_viewpager)

        viewPager.adapter = MyAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(viewPager)
    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragments[position].javaClass.simpleName
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}