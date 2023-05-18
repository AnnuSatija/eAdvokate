package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.InstanceServiceviewallAdapter
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.ServicesResponse
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class InstantServicesViewAllActivity : AppCompatActivity() {
    lateinit var instanceservicesrecyclerview: RecyclerView
    lateinit var instanceServiceAdapter: InstanceServiceviewallAdapter
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var searchlayout: LinearLayout
    lateinit  var notfoundlayoutcomplete: RelativeLayout
    var serviceList:MutableList<ServicesResponse> = arrayListOf()
    var check:String=""
    var type:String=""
    lateinit var skip:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_instant_services_view_all)
        if(intent.hasExtra("Check")){
            check=intent.getStringExtra("Check") as String
        }
        if(intent.hasExtra("type")){
           type=intent.getStringExtra("type")  as String
        }

        init()

    }

    fun  init(){
        sharedpreferences= Sharedpreferences.getInstance(this)
        if(!sharedpreferences!!.getServicesData().isNullOrEmpty()){
            serviceList=sharedpreferences!!.getServicesData()//
        }
        instanceservicesrecyclerview=findViewById(R.id.whatsissuerecyclerview)
        searchlayout=findViewById(R.id.searchlayout)
        notfoundlayoutcomplete=findViewById(R.id.notfoundlayoutcomplete)
        skip=findViewById(R.id.skip)
        searchlayout.visibility= View.GONE
        // skip is visible for all .................................................
        if(!check.isNullOrEmpty()){
            skip.visibility=View.VISIBLE
        }
        else{
            skip.visibility=View.VISIBLE
        }
        instanceservicesrecyclerview.layoutManager= GridLayoutManager(this,3)
        backarrow=findViewById(R.id.backarrow)
        if(serviceList.size>0) {
            notfoundlayoutcomplete.visibility = View.GONE
            instanceservicesrecyclerview.visibility = View.VISIBLE
            setInstanceServiceAdapter(serviceList)
        }else{
            notfoundlayoutcomplete.visibility = View.VISIBLE
            instanceservicesrecyclerview.visibility = View.GONE
        }

        backarrow.setOnClickListener {
            finish()
        }

        skip.setOnClickListener {
            for (service in serviceList)
            {
                if(service.service_name.equals("24x7 Legal Assistance")){
                    var serviceType=service.service_type
                    var serviceId=service.id
                    var intent= Intent(this@InstantServicesViewAllActivity, ForVideoAudioConsultationActivity::class.java)
                    intent.putExtra("service_type", serviceType)
                    intent.putExtra("service_id", serviceId)
                    if(!type.isNullOrBlank()){
                        intent.putExtra("callingtype",type)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    fun setInstanceServiceAdapter(list:MutableList<ServicesResponse>){
        instanceServiceAdapter= InstanceServiceviewallAdapter(this,list,emptyList(),"instance",type)
        instanceservicesrecyclerview.adapter=instanceServiceAdapter
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