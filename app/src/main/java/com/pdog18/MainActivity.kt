package com.pdog18

import androidx.fragment.app.Fragment
import com.pdog18.transition.AutoFragment
import com.pdog18.transition.ExplodeFragment
import com.pdog18.transition.ImageTransformFragment
import com.pdog18.transition.SlideFragment

class MainActivity : TabViewPagerActivity() {
    override val fragments: Array<Fragment> =
        arrayOf(
//            ImageTransformFragment(),
            ExplodeFragment(),
            AutoFragment(),
            SlideFragment()
        )
}

