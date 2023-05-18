package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.appsnipp.eadvokate.R


class ImageAdpter(private val context: Context, val imagelist: Array<Int>): PagerAdapter() {

    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return imagelist.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater!!.inflate(R.layout.pager_item, null)

        var imagepager=view.findViewById<ImageView>(R.id.pagerimage)
        imagepager.setImageResource(imagelist[position])

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}
