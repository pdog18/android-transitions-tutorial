package com.pdog18.transition

import androidx.fragment.app.Fragment
import com.pdog18.TabViewPagerActivity

class MainActivity : TabViewPagerActivity() {
    override val fragments: Array<Fragment> = arrayOf(AutoFragment())
}

