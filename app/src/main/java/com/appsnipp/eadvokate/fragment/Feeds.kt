package com.appsnipp.eadvokate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.InstanceServiceAdapter
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.utils.Constant

class Feeds : Fragment() {
    lateinit var feedlistrecyclerview:RecyclerView
    lateinit var feedadapter:InstanceServiceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
       // setAdapter(Constant.feedlist(requireContext()))
        return view
    }
    fun init(view: View){
       feedlistrecyclerview=view.findViewById(R.id.feedlistrecyclerview)
       feedlistrecyclerview.layoutManager=LinearLayoutManager(requireContext())
        feedlistrecyclerview.hasFixedSize()

    }

    fun setAdapter(list:List<InstanceServiceModel>){
        feedadapter= InstanceServiceAdapter(requireContext(),list, arrayListOf(),"feedslist")
        feedlistrecyclerview.adapter=feedadapter
        feedadapter.notifyDataSetChanged()
    }


}