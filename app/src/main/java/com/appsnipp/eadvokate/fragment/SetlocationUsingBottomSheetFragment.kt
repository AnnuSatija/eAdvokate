package com.appsnipp.eadvokate.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.appsnipp.eadvokate.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.File.separator
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class SetlocationUsingBottomSheetFragment : BottomSheetDialogFragment() {
    var address: String=""
    var latt:Double = 0.0
    var longg: Double =0.0
    var permissions1 = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)
    lateinit var currentlocadd:TextView
    lateinit var backarrow: CardView
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.bottomsheetlayout, container, false)
        init(view)
        //getLocation()
        setclicklistner()
        return view
    }

    fun init(view: View){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        currentlocadd=view.findViewById(R.id.currentlocadd)
        backarrow=view.findViewById(R.id.backarrow)
    }

    fun setclicklistner(){

       backarrow.setOnClickListener {
           dismiss()
       }

    }

   /* @SuppressLint("MissingPermission", "SetTextI18n")*/
  /*  private fun getLocation() {
        if (checkPermissions()) {
            if (GPSEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        list[0].latitude
                        list[0].longitude
                        currentlocadd.text=list[0].getAddressLine(0)

                    }
                }

            }
            else {
                GPSAlert()
            }

        }

    }

    fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions1) {
            result = ContextCompat.checkSelfPermission(requireContext(), p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(Activity(), listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }

    fun GPSEnabled():Boolean{
        val mLocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Checking GPS is enabled
        val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if(!mGPS && !network_enabled){
            GPSAlert()
        }
        return mGPS || network_enabled

    }

    fun GPSAlert(){
        val builder= AlertDialog.Builder(requireContext(),R.style.Theme_Transparent1).create()
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
*/

}