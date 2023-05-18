package com.appsnipp.eadvokate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.ImageAdpter
import com.appsnipp.eadvokate.adapter.InstanceServiceviewallAdapter
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.utils.Constant

class NearbyAdvocateActivity : AppCompatActivity() {

    lateinit var trendingadvocaterecyclerview: RecyclerView
    lateinit var instanceServiceAdapter: InstanceServiceviewallAdapter
    lateinit var nearbyadvoactelayout: RelativeLayout
    lateinit var searchadvoactelayout: RelativeLayout
    lateinit var searchadvoacteaddresslayout: RelativeLayout
    lateinit var backarrow: CardView
    lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_advocate)
        init()
        setTrendingAdvocateAdapter(Constant.trendingadvocate(this))
        setclicklistner()
    }

    fun init(){
        trendingadvocaterecyclerview=findViewById(R.id.nearbyadvocatelist)
        nearbyadvoactelayout=findViewById(R.id.nearbyadvoactelayout)
        searchadvoactelayout=findViewById(R.id.searchadvoactelayout)
        searchadvoacteaddresslayout=findViewById(R.id.searchadvoacteaddresslayout)
        backarrow=findViewById(R.id.backarrow)
        title=findViewById(R.id.title)
        trendingadvocaterecyclerview.layoutManager= LinearLayoutManager(this)
        searchadvoactelayout.visibility=View.GONE
        nearbyadvoactelayout.visibility=View.VISIBLE
        searchadvoacteaddresslayout.visibility=View.VISIBLE
        title.text=applicationContext.getString(R.string.nearbymsg)

    }

    fun setTrendingAdvocateAdapter(list:List<InstanceServiceModel>){
        instanceServiceAdapter= InstanceServiceviewallAdapter(this,arrayListOf(),list,"trendadvocate")
        trendingadvocaterecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()
    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }
    }


}