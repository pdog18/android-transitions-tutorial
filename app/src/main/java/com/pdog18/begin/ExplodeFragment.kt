package com.pdog18.begin

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.pdog.dimension.dp
import com.pdog18.helper.BaseFragment
import com.pdog18.helper.Layout
import com.pdog18.transition.R
import kotlinx.android.synthetic.main.explode.*


@Layout(R.layout.explode)
class ExplodeFragment : BaseFragment() {

    private val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
        @TargetApi(Build.VERSION_CODES.M)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val cell = TextView(parent.context)
            val viewHolder = object : RecyclerView.ViewHolder(cell) {}

            cell.apply {
                gravity = Gravity.CENTER
                setTextColor(context.getColor(android.R.color.white))
                setBackgroundColor(Color.parseColor("#FF5722"))
                layoutParams = RecyclerView.LayoutParams(80.dp, 80.dp).apply {
                    setMargins(5.dp, 5.dp, 5.dp, 5.dp)
                }
            }

            cell.setOnClickListener { onClick(it) }
            return viewHolder
        }

        override fun getItemCount() = 100

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            with(holder.itemView as TextView) {
                text = holder.adapterPosition.toString()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
        list.layoutManager = GridLayoutManager(context, 4)
    }

    private fun onClick(clickedView: View) {
        // save rect of view in screen coordinates
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)

        // create Explode transition with epicenter
        val explode = Explode().apply {
            epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition): Rect {
                    return viewRect
                }
            }
            duration = 1000
        }
        TransitionManager.beginDelayedTransition(list, explode)

        // remove all views from Recycler View
        list.adapter = null

        list.postDelayed({
            list.adapter = adapter
            adapter.notifyDataSetChanged()
        }, 2000L)
    }
}