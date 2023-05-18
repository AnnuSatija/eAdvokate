package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.ImageAdpter
import com.appsnipp.eadvokate.adapter.InstanceServiceviewallAdapter
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class MyAdvocatesACtivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit var myadvocaterecyclerview: RecyclerView
    lateinit var instanceServiceAdapter: InstanceServiceviewallAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_my_advocates)

        init()
        setTrendingAdvocateAdapter(Constant.trendingadvocate(this))
        setclicklistner()

    }

    fun init(){
        backarrow=findViewById(R.id.backarrow)
        myadvocaterecyclerview=findViewById(R.id.myadvocatedlistrecyclerview)
        myadvocaterecyclerview.layoutManager= LinearLayoutManager(this)
    }

    fun setclicklistner() {
        backarrow.setOnClickListener { finish() }

    }

    fun setTrendingAdvocateAdapter(list:List<InstanceServiceModel>){
        instanceServiceAdapter= InstanceServiceviewallAdapter(this,arrayListOf(),list,"myadvocate")
        myadvocaterecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()
    }

    fun loadlocale() {
        sharedpreferences = Sharedpreferences.getInstance(applicationContext)
        val language: String = sharedpreferences!!.getlanguage("MyLanguage").toString()
        setLocale(language)
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        applicationContext.resources.updateConfiguration(configuration, applicationContext.resources.displayMetrics)
        sharedpreferences!!.setlanguage("MyLanguage", lang)
    }
}