package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.databinding.ItemInfiniteScrollListHorizontalBinding
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.scrollview.InfiniteAutoScrollRecyclerView

class InfiniteAutoScrollAdapter(private val evenLayoutResId: Int) : RecyclerView.Adapter<InfiniteAutoScrollAdapter.InfiniteAutoScrollViewHolder>() {
    private var images = arrayListOf<InstanceServiceModel>()

    fun notifyData(images: MutableList<InstanceServiceModel>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): InfiniteAutoScrollViewHolder {
        return InfiniteAutoScrollViewHolder(LayoutInflater.from(viewGroup.context).inflate(evenLayoutResId, viewGroup, false)
        )
    }

    override fun onBindViewHolder(holder: InfiniteAutoScrollViewHolder, position: Int) {
        holder.imageView.setImageResource(images.get(position % images.size).image)
        holder.servicename.setText(images.get(position % images.size).name)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class InfiniteAutoScrollViewHolder(@NonNull view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.infiniteScrollImage)
        val servicename: TextView = view.findViewById(R.id.servicestxt)
    }

}