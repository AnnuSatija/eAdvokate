package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.ServicePackegesResponse
import com.appsnipp.eadvokate.utils.Sharedpreferences


class ServicePackagesAdapter(val context: Context, val packageList: MutableList<ServicePackegesResponse>) : RecyclerView.Adapter<ServicePackagesAdapter.ModelViewHolder>() {
    var row_index =-1
    var sharedpreferences=Sharedpreferences.getInstance(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_service_package_layout, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return packageList.size
    }

    override fun onBindViewHolder(holder: ServicePackagesAdapter.ModelViewHolder, position: Int) {

     holder.package_type.text = packageList.get(position).package_type
     holder.experience.text = "Experience -"+packageList.get(position).experience +"Years"
     holder.price.text = "Charges - Rs."+packageList.get(position).price +"/page"

     holder.itemView.setOnClickListener {
         row_index = position;
         notifyDataSetChanged();
     }
        if(row_index==holder.adapterPosition){
             holder.basiclayout.setBackgroundResource(R.drawable.selectlanguageborder)
             holder.package_type.setTextColor(context.getColor(R.color.red))
             holder.experience.setTextColor(context.getColor(R.color.red))
             holder.price.setTextColor(context.getColor(R.color.red))
             holder.basicview.setBackgroundColor(context.getColor(R.color.red))
             sharedpreferences.savePosition("packageposition",row_index)
         }
         else{
             holder.basiclayout.setBackgroundResource(R.drawable.expeadvocateback)
             holder.package_type.setTextColor(context.getColor(R.color.greydark))
             holder.experience.setTextColor(context.getColor(R.color.greydark))
             holder.price.setTextColor(context.getColor(R.color.greydark))
             holder.basicview.setBackgroundColor(context.getColor(R.color.greydark))
         }
    }

    class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var package_type=itemView.findViewById<TextView>(R.id.package_type)
        var experience=itemView.findViewById<TextView>(R.id.experience)
        var price=itemView.findViewById<TextView>(R.id.price)
        var basiclayout=itemView.findViewById<LinearLayout>(R.id.basiclayout)
        var basicview=itemView.findViewById<View>(R.id.basicview)
    }

}