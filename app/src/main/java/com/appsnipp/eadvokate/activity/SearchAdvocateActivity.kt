package com.appsnipp.eadvokate.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class SearchAdvocateActivity : AppCompatActivity() {
    lateinit var currentaddress:TextView
    lateinit var englishtxt:TextView
    lateinit var hinditxt:TextView
    lateinit var onlinetxt:TextView
    lateinit var personaltxt:TextView
    lateinit var continuetext:TextView
    lateinit var courtnamespinner:Spinner
    lateinit var backarrow:CardView
    lateinit var nearbyadvoactelayout: RelativeLayout
    lateinit var searchadvoactelayout: RelativeLayout
    var permissions1 = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var sharedpreferences: Sharedpreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_search_advocate)
        init()
        //getLocationn()
        setclicklistner()

    }

    fun  init(){
        currentaddress=findViewById(R.id.currentaddress)
        backarrow=findViewById(R.id.backarrow)
        englishtxt=findViewById(R.id.englishtxt)
        hinditxt=findViewById(R.id.hinditxt)
        onlinetxt=findViewById(R.id.onlinetxt)
        personaltxt=findViewById(R.id.personaltxt)
        continuetext=findViewById(R.id.continuetext)
        courtnamespinner=findViewById(R.id.courtnamespinner)
        nearbyadvoactelayout=findViewById(R.id.nearbyadvoactelayout)
        searchadvoactelayout=findViewById(R.id.searchadvoactelayout)
        searchadvoactelayout.visibility=View.VISIBLE
        nearbyadvoactelayout.visibility=View.GONE
    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }
        englishtxt.setOnClickListener {
            englishtxt.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setBackgroundResource(R.drawable.edittextback)
            englishtxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            hinditxt.setTextColor(Color.BLACK)

        }


        hinditxt.setOnClickListener {
            hinditxt.setBackgroundResource(R.drawable.selectlanguageborder)
            englishtxt.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            englishtxt.setTextColor(Color.BLACK)


        }

        onlinetxt.setOnClickListener {
            onlinetxt.setBackgroundResource(R.drawable.selectlanguageborder)
            personaltxt.setBackgroundResource(R.drawable.edittextback)
            onlinetxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            personaltxt.setTextColor(Color.BLACK)

        }


        personaltxt.setOnClickListener {
            personaltxt.setBackgroundResource(R.drawable.selectlanguageborder)
            onlinetxt.setBackgroundResource(R.drawable.edittextback)
            personaltxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            onlinetxt.setTextColor(Color.BLACK)

        }

        continuetext.setOnClickListener {
            var intent=Intent(this@SearchAdvocateActivity,NearbyAdvocateActivity::class.java)
            startActivity(intent)
        }
    }
   /* fun getLocationn() {
        if (checkPermissions()) {
            if (GPSEnabled()) {
                var locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager?

                var locationListener = object : LocationListener {

                    override fun onLocationChanged(location: Location) {
                        try {
                            var latitute = location!!.latitude
                            var longitute = location!!.longitude

                            val geocoder = Geocoder(this@SearchAdvocateActivity, Locale.getDefault())
                            val list: List<Address> =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            list[0].latitude
                            list[0].longitude
                            currentaddress.text = list[0].getAddressLine(0)
                                .split(",")[1] + "," + list[0].getAddressLine(0).split(",")[3]

                        }catch (exception:Exception){
                            exception.toString()
                        }
                    }

                    override
                    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                }

                try {
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                } catch (ex:SecurityException) {
                    Toast.makeText(this@SearchAdvocateActivity, "Error in getting address!", Toast.LENGTH_SHORT).show()
                }
            } else {
                GPSAlert()
            }
        }
    }

    fun GPSEnabled():Boolean{
        val mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Checking GPS is enabled
        val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(!mGPS && !network_enabled){
            GPSAlert()
        }
        return mGPS || network_enabled

    }

    fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions1) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }

    fun GPSAlert(){
        val builder= AlertDialog.Builder(this,R.style.Theme_Transparent1).create()
        val view = layoutInflater.inflate(R.layout.gpsalert,null)

        var window = builder.window

        window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        var okay=view.findViewById<TextView>(R.id.accept)

        okay.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            builder.dismiss()
        }
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }*/

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