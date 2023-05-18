package com.appsnipp.eadvokate.activity

import android.graphics.Interpolator
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.fragment.SetlocationUsingBottomSheetFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity : AppCompatActivity(),OnMapReadyCallback {
    lateinit var backarrow: CardView
    var latt:Double = 0.0
    var longg: Double =0.0
    var address: String=""
    private lateinit var mMap: GoogleMap
    lateinit var address1: TextView
    lateinit var address2:TextView
    lateinit var change:TextView
    var count:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_map)
        getIntentValue()
        init()
        setclicklistner()

    }

    fun getIntentValue(){
        var bundle=intent.extras
        if(bundle!=null) {
            latt = bundle!!["lat"] as Double
            longg = bundle!!["long"] as Double
            address = bundle!!["address"] as String
        }
    }

    fun init(){
        backarrow=findViewById(R.id.backarrow)
        address1=findViewById(R.id.address1)
        address2=findViewById(R.id.address2)
        change=findViewById(R.id.change)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun setclicklistner() {
        backarrow.setOnClickListener {
            finish()
        }
        change.setOnClickListener {
            val option = SetlocationUsingBottomSheetFragment()
            option.show(supportFragmentManager, "SelectOption")
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val latlong = LatLng(latt, longg)
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = false
        mMap.uiSettings.isZoomGesturesEnabled = true
        val cameraPosition = CameraPosition.Builder().target(latlong) // Sets the center of the map to location user
            .zoom(17f) // Sets the zoom
            .bearing(90f) // Sets the orientation of the camera to east
            .tilt(40f) // Sets the tilt of the camera to 30 degrees
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        address1.text=address.split(",")[1]
        address2.text=address
        if(count==0){
            setMarkerBounce(mMap,latlong)
        }
        else{
            mMap.addMarker(MarkerOptions().position(latlong).title(address))
        }

    }

    private fun setMarkerBounce(mMap:GoogleMap,latlong:LatLng) {
        val handler = Handler()
        val startTime = SystemClock.uptimeMillis()
        val duration: Long = 1000
        val Interpolator = BounceInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - startTime
                val t = maxOf(1 - Interpolator.getInterpolation(elapsed.toFloat() / duration)) as Float
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(latlong).title(address))!!.setAnchor(0.5f, 1.0f+t )
                if (t > 0.0) {
                    handler.postDelayed(this, 5)

                } else {
                    if(count<2) {
                        setMarkerBounce(mMap, latlong)
                        count++
                    }
                }

            }
        })
    }



}
