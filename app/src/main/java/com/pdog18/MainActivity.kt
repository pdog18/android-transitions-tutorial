package com.pdog18

import androidx.fragment.app.Fragment
import com.pdog18.begin.*
import com.pdog18.begin.SlideFragment
import com.pdog18.custom.ScaleFragment
import com.pdog18.go.GoAutoFragment

class MainActivity : TabViewPagerActivity() {
    override val fragments: Array<Fragment> =
        arrayOf(
            GoAutoFragment(),
            SetFragment(),
            ExcludeTargetFragment(),
            PathFragment(),
            ImageTransformFragment(),
            ExplodeFragment(),
            AutoFragment(),
            ScaleFragment(),
            SlideFragment()
        )
}

