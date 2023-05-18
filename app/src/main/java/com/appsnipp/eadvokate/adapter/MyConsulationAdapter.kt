package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.databinding.ConsulationItemLayoutBinding
import com.appsnipp.eadvokate.model.ConsulationResponse
import com.bumptech.glide.Glide

class MyConsulationAdapter(val context:Context,val consulationList:MutableList<ConsulationResponse>):RecyclerView.Adapter<MyConsulationAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ConsulationItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.advocatename.text="Adv "+consulationList[position].advocate_name
    holder.binding.exp.text="Experience - " +consulationList[position].experience
    holder.binding.date.text="Date - " +consulationList[position].date_time
    holder.binding.consullanguage.text="Consultation Language - " +consulationList[position].consult_language
    holder.binding.consulmode.text="Consultation Mode - " +consulationList[position].consultancy_mode
    holder.binding.servicename.text=consulationList[position].service_name
    Glide.with(context).load(consulationList[position].photo).into(holder.binding.advocateimage)

    }

    override fun getItemCount(): Int {
      return  consulationList.size
    }

    class ViewHolder(val binding: ConsulationItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

}