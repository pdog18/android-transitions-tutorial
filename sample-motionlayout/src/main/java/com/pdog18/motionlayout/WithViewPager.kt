package com.pdog18.motionlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_with_view_pager.*

class WithViewPager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_view_pager)

        view_pager.adapter = object : PagerAdapter() {
            override fun getCount() = 2

            override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
                container.removeView(any as View)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = ImageView(container.context)
                view.setImageResource(R.mipmap.ic_launcher)
                container.addView(view)
                return view
            }

            override fun isViewFromObject(view: View, any: Any) = view == any
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                (motion_layout_with_view_pager as MotionLayout).progress = (position + positionOffset)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }
}
