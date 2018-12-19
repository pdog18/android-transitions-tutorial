package com.pdog18

import androidx.fragment.app.Fragment
import com.pdog18.api.ExcludeTargetFragment
import com.pdog18.transition.*

class MainActivity : TabViewPagerActivity() {
    override val fragments: Array<Fragment> =
        arrayOf(
            ExcludeTargetFragment(),
            PathFragment(),
            ImageTransformFragment(),
            ExplodeFragment(),
            AutoFragment(),
            SlideFragment()
        )
}

