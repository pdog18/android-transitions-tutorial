package com.pdog18.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getClassAnnotation<Layout>().layoutId, container, false)
    }


    private inline fun <reified T : Annotation> Any.getClassAnnotation(): T {
        return this::class.java.getAnnotation(T::class.java)!!
    }
}
