package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.SearchAdvocateActivity
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.InstanceServiceOnlyNameModel


class WhatsissueServiceOnlyNameAdapter(val context: Context, val categoriesList: List<InstanceServiceOnlyNameModel>, val type:String):RecyclerView.Adapter<WhatsissueServiceOnlyNameAdapter.ViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsissueServiceOnlyNameAdapter.ViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.whatsissueitemlayout, parent, false)
         return ViewHolder(view)
     }

     override fun onBindViewHolder(holder: WhatsissueServiceOnlyNameAdapter.ViewHolder, position: Int) {

         holder.linearlayout.visibility=View.GONE
         holder.whatsissuelayout.visibility=View.GONE
         holder.onlynamecard.visibility=View.VISIBLE
         if(type.equals("explore")){
             holder.textView.visibility=View.GONE
             holder.explorename.visibility=View.VISIBLE
             holder.explorename.text=categoriesList.get(position).name
         }
         else{
             holder.textView.visibility=View.VISIBLE
             holder.explorename.visibility=View.GONE
             holder.textView.text=categoriesList.get(position).name
             holder.itemView.setOnClickListener {
                 var intent=Intent(context,SearchAdvocateActivity::class.java)
                 context.startActivity(intent)
             }

         }
     }

     override fun getItemCount(): Int {
         return categoriesList.size
     }

     class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val textView: TextView = itemView.findViewById(R.id.name)
         val explorename: TextView = itemView.findViewById(R.id.explorename)
         val linearlayout:CardView=itemView.findViewById(R.id.instancelayout)
         val whatsissuelayout:CardView=itemView.findViewById(R.id.whatsnewlayout)
         val onlynamecard:CardView=itemView.findViewById(R.id.onlynamecard)

     }

 }