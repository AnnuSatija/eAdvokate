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
import com.appsnipp.eadvokate.adapter.WhatsissueServiceOnlyNameAdapter
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.InstanceServiceOnlyNameModel
import com.appsnipp.eadvokate.utils.Constant

class Whatsissuefragment : Fragment() {
    lateinit var whatsissuerecyclerview: RecyclerView
    lateinit var instanceServiceAdapter: WhatsissueServiceOnlyNameAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val  view=inflater.inflate(R.layout.fragment_whatsissuefragment, container, false)
        init((view))
        setwhatissueAdapter(Constant.loadWhatsIssueServicesOnlynameList(requireContext()))
        return view
    }

    fun init(view: View){
        whatsissuerecyclerview=view.findViewById(R.id.whatsissuerecyclerview)
        whatsissuerecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    fun setwhatissueAdapter(list:List<InstanceServiceOnlyNameModel>){
        instanceServiceAdapter= WhatsissueServiceOnlyNameAdapter(requireActivity(),list,"whatsissue")
        whatsissuerecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()

    }



}