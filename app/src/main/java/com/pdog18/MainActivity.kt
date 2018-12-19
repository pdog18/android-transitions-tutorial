package com.pdog18

import androidx.fragment.app.Fragment
import com.pdog18.begin.*
import com.pdog18.begin.SlideFragment
import com.pdog18.constraint.ConstranitSetFragment
import com.pdog18.custom.ScaleFragment
import com.pdog18.go.GoAutoFragment
import com.pdog18.helper.TabViewPagerActivity

class MainActivity : TabViewPagerActivity() {
    override val fragments: Array<Fragment> =
        arrayOf(
            ConstranitSetFragment(),
            ScaleFragment(),
            GoAutoFragment(),
            SetFragment(),
            ExcludeTargetFragment(),
            PathFragment(),
            ImageTransformFragment(),
            ExplodeFragment(),
            AutoFragment(),
            SlideFragment()
        )
}

