package com.appsnipp.eadvokate.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class SetLocation : AppCompatActivity() {
    lateinit var backarrow: CardView
    var sharedpreferences: Sharedpreferences? = null
    lateinit var currentaddresslayout:LinearLayout
    lateinit var currentlocadd:TextView
    var permissions1 = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
      var latt:Double = 0.0
    var longg: Double =0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadlocale()
        setContentView(R.layout.activity_set_location)

        init()
       // getLocation()
        setclicklistner()
    }

    fun init(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        backarrow=findViewById(R.id.backarrow)
        currentaddresslayout=findViewById(R.id.currentaddresslayout)
        currentlocadd=findViewById(R.id.currentlocadd)

    }


  /*  @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (GPSEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        latt= list[0].latitude
                        longg = list[0].longitude
                        currentlocadd.text=list[0].getAddressLine(0)

                    }
                }

            }
            else {
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
            ActivityCompat.requestPermissions(Activity(), listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0])

        }
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

    fun setclicklistner() {
        backarrow.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
        }
        currentaddresslayout.setOnClickListener {
            if(latt>0.0 && longg>0.0) {
                var intent = Intent(this@SetLocation, MapActivity::class.java)
                intent.putExtra("lat", latt)
                intent.putExtra("long", longg)
                intent.putExtra("address", currentlocadd.text.toString())
                startActivity(intent)

            }
        }
    }
}