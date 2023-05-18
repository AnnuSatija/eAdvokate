package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.BannerResponse
import com.bumptech.glide.Glide

class HomaPageViewPagerAdapter(val viewPager2: ViewPager2,val context: Context, val imageimagelist: MutableList<BannerResponse>) : RecyclerView.Adapter<HomaPageViewPagerAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pager_item, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageimagelist.size
    }

    override fun onBindViewHolder(holder: HomaPageViewPagerAdapter.ModelViewHolder, position: Int) {
        Glide.with(context).load(imageimagelist[position].image).into(holder.img)
        if(position==imageimagelist.size-2){
          viewPager2.post(run)
        }
    }

    val run=object :Runnable{

        override fun run() {
            imageimagelist.addAll(imageimagelist)
            notifyDataSetChanged()
        }

    }

    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.pagerimage)

    }

}