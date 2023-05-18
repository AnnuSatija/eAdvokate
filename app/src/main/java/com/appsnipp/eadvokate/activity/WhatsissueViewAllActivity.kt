package com.appsnipp.eadvokate.activity

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.InstanceServiceAdapter
import com.appsnipp.eadvokate.adapter.InstanceServiceviewallAdapter
import com.appsnipp.eadvokate.adapter.WhatsissueServiceOnlyNameAdapter
import com.appsnipp.eadvokate.fragment.Whatsissuefragment
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.InstanceServiceOnlyNameModel
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*
import kotlin.collections.ArrayList

class WhatsissueViewAllActivity : AppCompatActivity() {
    lateinit var whatsissuerecyclerview: RecyclerView
    lateinit var instanceServiceAdapter: WhatsissueServiceOnlyNameAdapter
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit var title:TextView
    lateinit var searchtxt:EditText
    lateinit var list:List<InstanceServiceOnlyNameModel>
    var searchlist:MutableList<InstanceServiceOnlyNameModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_instant_services_view_all)
        init()
        list=Constant.loadWhatsIssueServicesOnlynameList(this)
        if(searchlist.size==0) {
            setwhatissueAdapter(Constant.loadWhatsIssueServicesOnlynameList(this))
        }
        setclicklistner()
    }

    fun  init(){
        whatsissuerecyclerview=findViewById(R.id.whatsissuerecyclerview)
        whatsissuerecyclerview.layoutManager = LinearLayoutManager(this)
        backarrow=findViewById(R.id.backarrow)
        searchtxt=findViewById(R.id.searchtext)
        title=findViewById(R.id.title)
        title.text=getString(R.string.whatsissue)

    }

    fun setwhatissueAdapter(list:List<InstanceServiceOnlyNameModel>){
        instanceServiceAdapter= WhatsissueServiceOnlyNameAdapter(this,list,"whatsissue")
        whatsissuerecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()

    }

    fun setclicklistner(){

        backarrow.setOnClickListener {
          /*val fragment=supportFragmentManager.findFragmentByTag("whatsissuefragment")
            if (fragment is Whatsissuefragment) {
                fragment.requireFragmentManager().popBackStack();
            }
            else{*/
                finish()
           // }
        }

        searchtxt.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchlist.clear()
                if(searchtxt.text.length>0) {
                    for (i in list) {
                        if (i.name.toString().toLowerCase().contains(searchtxt.text.toString().toLowerCase())) {
                            searchlist.add(i)
                        }
                    }
                    instanceServiceAdapter= WhatsissueServiceOnlyNameAdapter(this@WhatsissueViewAllActivity,searchlist,"whatsissue")
                    whatsissuerecyclerview.adapter=instanceServiceAdapter
                    instanceServiceAdapter.notifyDataSetChanged()
                }
                else{
                    instanceServiceAdapter= WhatsissueServiceOnlyNameAdapter(this@WhatsissueViewAllActivity,list,"whatsissue")
                    whatsissuerecyclerview.adapter=instanceServiceAdapter
                    instanceServiceAdapter.notifyDataSetChanged()
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

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

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            add(fragment,"whatsissuefragment")
            addToBackStack("whatsissuefragment")
            replace(R.id.framelayout, fragment)
            commit()
        }
}