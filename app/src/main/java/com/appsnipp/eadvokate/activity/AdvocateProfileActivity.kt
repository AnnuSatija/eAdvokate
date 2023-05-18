package com.appsnipp.eadvokate.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.*
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.model.CityResponse
import com.appsnipp.eadvokate.model.ProfiledataModel
import com.appsnipp.eadvokate.model.State
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AdvocateProfileActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var name: EditText
    lateinit  var mobileno: EditText
    lateinit  var emailid: EditText
    lateinit  var advname: TextView
    lateinit  var advmob: TextView
    lateinit  var statespinner: Spinner
    lateinit  var cityspinner: Spinner
    lateinit var apiHolder: ApiHolder
    var stateList:List<State> = arrayListOf()
    var stateTitle:MutableList<String> = ArrayList()
    var stateId:MutableList<Int> = ArrayList()
    var cityList:List<CityResponse> = arrayListOf()
    var cityName:MutableList<String> = ArrayList()
    var cityId:MutableList<Int> = ArrayList()
    var accessToken:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_advocate_profile)
        init()
        GetProfileData()
        setclicklistner()
    }

    fun  init(){
        apiHolder= ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences= Sharedpreferences.getInstance(this)
        accessToken=sharedpreferences!!.getAccessToken("connect_sid")
        backarrow=findViewById(R.id.backarrow)
        name=findViewById(R.id.name)
        mobileno=findViewById(R.id.mobileno)
        emailid=findViewById(R.id.emailid)
        statespinner=findViewById(R.id.statespinner)
        cityspinner=findViewById(R.id.cityspinner)
        advname=findViewById(R.id.advname)
        advmob=findViewById(R.id.advmob)

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }
      /*  statespinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              HitApiForCity(stateId.get(position))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }*/

    }

    fun GetProfileData(){
        Constant.loadingpopup(this@AdvocateProfileActivity,"Loading Profile Data","",false)
        var call=apiHolder.GetProfile(Constant.HeadersWithAccess(accessToken))
        call.enqueue(object :Callback<ProfiledataModel>{
            override fun onResponse(call: Call<ProfiledataModel>?, response: Response<ProfiledataModel>?) {
                Constant.StopLoader()
                if(response!!.isSuccessful){
                    var getdata = response.body()
                    var errorcode = getdata!!.status
                    var userinfo=getdata.response
                    if(errorcode==200){
                        var Name= userinfo.name
                        var MobNo= userinfo.contact_number
                        var Email= userinfo.email

                        name.setText(Name)
                        advname.setText(Name)
                        mobileno.setText(MobNo)
                        advmob.setText(MobNo)
                        emailid.setText(Email)
                        //HitApiForState()
                        disableEditField()

                    }
                }
                else{

                }
            }

            override fun onFailure(call: Call<ProfiledataModel>?, t: Throwable?) {


            }

        })
    }

 /*   fun HitApiForState(){
        stateTitle.clear()
        stateId.clear()
        var call =apiHolder.GetState(Constant.Headers())
        call.enqueue(object :Callback<StateDataModel>{
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<StateDataModel>?, response: Response<StateDataModel>?) {
                if(response!!.isSuccessful){
                    var getdata=response.body()
                    var errorcode=getdata!!.status
                    if(errorcode==200){
                     stateList=getdata!!.response
                        if(stateList.size>0){
                            for (i in stateList){
                                stateTitle.add(i.state_title)
                                stateId.add(i.id)
                            }
                            if (statespinner != null) {
                                val adapter = ArrayAdapter(this@AdvocateProfileActivity, android.R.layout.simple_spinner_item, stateTitle)
                                statespinner.adapter = adapter
                            }
                        }
                    }
                }
                else{

                }

            }

            override fun onFailure(call: Call<StateDataModel>?, t: Throwable?) {

            }

        })
    }

    fun HitApiForCity(stateId:Int){
        cityName.clear()
        cityId.clear()
        var call =apiHolder.GetCity(Constant.Headers(),stateId)
        call.enqueue(object :Callback<CityDataModel>{
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<CityDataModel>?, response: Response<CityDataModel>?) {
                if(response!!.isSuccessful){
                    var getdata=response.body()
                    var errorcode=getdata!!.status
                    if(errorcode==200){
                        cityList=getdata!!.response
                        if(cityList.size>0){
                            for (i in cityList){
                                cityName.add(i.district_name)
                                cityId.add(i.id)
                            }
                            if (statespinner != null) {
                                val adapter = ArrayAdapter(this@AdvocateProfileActivity, android.R.layout.simple_spinner_item, cityName)
                                cityspinner.adapter = adapter
                            }
                        }
                    }
                }
                else{

                }

            }

            override fun onFailure(call: Call<CityDataModel>?, t: Throwable?) {

            }

        })
    }*/

    fun disableEditField(){
        name.isEnabled=false
        emailid.isEnabled=false
        mobileno.isEnabled=false
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