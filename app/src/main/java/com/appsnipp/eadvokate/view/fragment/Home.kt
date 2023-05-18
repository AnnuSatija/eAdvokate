package com.appsnipp.eadvokate.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Rect
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.*
import com.appsnipp.eadvokate.adapter.HomaPageViewPagerAdapter
import com.appsnipp.eadvokate.adapter.InfiniteAutoScrollAdapter
import com.appsnipp.eadvokate.adapter.InstanceServiceAdapter
import com.appsnipp.eadvokate.model.BannerResponse
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.ServicesResponse
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.scrollview.InfiniteAutoScrollRecyclerView
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class Home : Fragment() {
    lateinit var viewpager: ViewPager2
    lateinit var instanceservicesrecyclerview: RecyclerView
    lateinit var trendingservices: InfiniteAutoScrollRecyclerView
    //lateinit var trendingservices: RecyclerView
    lateinit var knowrightsrecyclerview: RecyclerView
    lateinit var videocalllayout: LinearLayout
    lateinit var audiocalllayout: LinearLayout
    lateinit var schedullayout: LinearLayout
    lateinit var legalassistancelayout: LinearLayout
    lateinit var getcurrentlocationlayout: LinearLayout
    lateinit var instanceServiceAdapter: InstanceServiceAdapter
    lateinit var viewallinstance: TextView
    lateinit var currentaddress: TextView
    lateinit var username: TextView
    lateinit var card: CardView
    lateinit var whatsissueimage: ImageView
    lateinit var douknowimage: ImageView
    lateinit var instancecard: CardView
   // val imagelist = arrayOf(R.drawable.bannerhomea, R.drawable.homebanner, R.drawable.bannerhomeb)
   /* var permissions1 = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )*/
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var layoutManager: LayoutManager
    var sharedpreferences: Sharedpreferences? = null
    lateinit var apiHolder: ApiHolder
    var accessToken: String = ""
    var serviceList: MutableList<ServicesResponse>? = arrayListOf()
    var onlysixserviceList: MutableList<ServicesResponse> = arrayListOf()
    lateinit var shimmer: ShimmerFrameLayout
    lateinit var viewModel: ViewModelClass
    var serviceresponselist: MutableList<ServicesResponse> = mutableListOf()
    var dashboardBannerList: MutableList<BannerResponse>? = mutableListOf()
    lateinit var bannerhandler:android.os.Handler
    lateinit var bannerrunnable:Runnable
    lateinit var infiniteRecyclerviewLayout:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        HitApiForServices()
        setknowrightsServicesAdapter(Constant.knowrightsSercives(requireContext()))
        clicklistner()
        //getLocationn()
        return view

    }

    fun init(view: View) {
        apiHolder = ApiClient.getApiClient(Constant.Authusername, resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences = Sharedpreferences.getInstance(requireContext())
        viewModel = ViewModelProviders.of(this)[ViewModelClass::class.java]
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        Log.d("Key", resources.getString(R.string.authpassword));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        viewpager = view.findViewById(R.id.viewPager)
        instanceservicesrecyclerview = view.findViewById(R.id.instanceservicesview)
        videocalllayout = view.findViewById(R.id.videocalllayout)
        audiocalllayout = view.findViewById(R.id.audiocalllayout)
        shimmer = view.findViewById(R.id.shimmerFrameLayout)
        instancecard = view.findViewById(R.id.instancecard)
        username = view.findViewById(R.id.name)
        infiniteRecyclerviewLayout = view.findViewById(R.id.infiniteRecyclerviewLayout)
        card = view.findViewById(R.id.card)


        var name = sharedpreferences!!.getUserInfo("name")
        username.text = "Hi, $name"

        trendingservices = view.findViewById(R.id.recyclerView)
        trendingservices.setOnTouchListener { v, event -> true }
        trendingservices.startScrolling(Constant.treandingSercives(requireContext()))

        knowrightsrecyclerview = view.findViewById(R.id.knowrightsrecyclerview)
        //  getcurrentlocationlayout=view.findViewById(R.id.getcurrentlocationlayout)
        schedullayout = view.findViewById(R.id.schedullayout)
        //  belllayout=view.findViewById(R.id.belllayout)
        //        currentaddress=view.findViewById(R.id.currentaddress)
        whatsissueimage = view.findViewById(R.id.whatsissueimage)
        douknowimage = view.findViewById(R.id.douknowimage)
        legalassistancelayout = view.findViewById(R.id.legalassistancelayout)
        instanceservicesrecyclerview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        knowrightsrecyclerview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewallinstance = view.findViewById(R.id.viewallinstance)
        setupCarousel(viewpager)
        viewModel.getBannerDataList(requireContext()).observe(requireActivity(), androidx.lifecycle.Observer {
            banner->
            var errorcode=banner.status
            if(errorcode==200) {
                dashboardBannerList = banner.response
                if (!dashboardBannerList.isNullOrEmpty()) {
                    sharedpreferences!!.saveDashboardData(dashboardBannerList)
                    val adapter = HomaPageViewPagerAdapter(viewpager,requireActivity(), dashboardBannerList!!)
                    viewpager.adapter = adapter
                }
            }
        })
        bannerhandler= android.os.Handler()
        bannerrunnable= Runnable {
            viewpager.currentItem=viewpager.currentItem+1
        }

        viewpager.registerOnPageChangeCallback(
            object :ViewPager2.OnPageChangeCallback(){

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }


                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bannerhandler.removeCallbacks(bannerrunnable)
                    bannerhandler.postDelayed(bannerrunnable,2000)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            }
        )

        }

    fun setInstanceServiceAdapter(list: MutableList<ServicesResponse>) {
        instanceServiceAdapter = InstanceServiceAdapter(requireActivity(), emptyList(), list, "instance")
        instanceservicesrecyclerview.adapter = instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()

    }

    /* fun settrendingServicesAdapter(list:MutableList<InstanceServiceModel>){
         instanceServiceAdapter= InstanceServiceAdapter(requireActivity(),list,"trendingservices")
         trendingservices.adapter=instanceServiceAdapter
         instanceServiceAdapter.notifyDataSetChanged()

     }*/

    fun setknowrightsServicesAdapter(list: List<InstanceServiceModel>) {
        instanceServiceAdapter = InstanceServiceAdapter(
            requireActivity(),
            list,
            serviceresponselist,
            "knowrightsservices"
        )
        knowrightsrecyclerview.adapter = instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()

    }

    fun clicklistner() {

        viewallinstance.setOnClickListener {
            var intent = Intent(requireContext(), InstantServicesViewAllActivity::class.java)
            intent.putExtra("Check","ViewAll")
            startActivity(intent)
        }

        videocalllayout.setOnClickListener {
            var intent = Intent(requireContext(), InstantServicesViewAllActivity::class.java)
            intent.putExtra("type","Video")
            startActivity(intent)
        }

        audiocalllayout.setOnClickListener {
            var intent = Intent(requireContext(), InstantServicesViewAllActivity::class.java)
            intent.putExtra("type","Audio")
            startActivity(intent)
        }

        schedullayout.setOnClickListener {
            var intent = Intent(requireContext(), WhatsissueViewAllActivity::class.java)
            startActivity(intent)
        }

        whatsissueimage.setOnClickListener {
            var intent = Intent(requireContext(), WhatsissueViewAllActivity::class.java)
            startActivity(intent)
        }

        /* belllayout.setOnClickListener {
             var intent= Intent(requireContext(), NotificationActivity::class.java)
             startActivity(intent)
         }*/

        /* getcurrentlocationlayout.setOnClickListener {
             if(GPSEnabled()) {
                 var intent = Intent(requireContext(), SetLocation::class.java)
                 startActivity(intent)
                 requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
             }
         }*/

        douknowimage.setOnClickListener {
            var intent = Intent(requireContext(), DidYouKnowActivity::class.java)
            startActivity(intent)
        }

        legalassistancelayout.setOnClickListener {
            //getlegalassistanceloaderaler()
            var intent = Intent(requireContext(), InstantServicesViewAllActivity::class.java)
            startActivity(intent)
        }

    }

    fun getloaderaler() {
        val builder = AlertDialog.Builder(requireContext(), R.style.Theme_Transparent1).create()
        val view = layoutInflater.inflate(R.layout.timerclock, null)
        var cancel = view.findViewById<CardView>(R.id.backarrow)
        var englistcharimage = view.findViewById<ImageView>(R.id.englishcharimage)
        var hindicharimage = view.findViewById<ImageView>(R.id.hindicharimage)
        var englisttxt = view.findViewById<TextView>(R.id.englisttxt)
        var hinditxt = view.findViewById<TextView>(R.id.hinditxt)
        var hindicard = view.findViewById<LinearLayout>(R.id.hindicard)
        var englishcard = view.findViewById<LinearLayout>(R.id.englishcard)
        var continuebutton = view.findViewById<TextView>(R.id.contbutton)

        cancel.setOnClickListener {
            builder.dismiss()
        }

        hindicard.setOnClickListener {
            hindicard.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            englishcard.setBackgroundResource(R.drawable.edittextback)
            englisttxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }


        englishcard.setOnClickListener {
            englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
            englisttxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            hindicard.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }

        continuebutton.setOnClickListener {
            var intent = Intent(requireContext(), YourAdvocateActivity::class.java)
            startActivity(intent)
        }

        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }


/*    fun GPSEnabled(): Boolean {
        val mLocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Checking GPS is enabled
        val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!mGPS && !network_enabled) {
            GPSAlert()
        }
        return mGPS || network_enabled

    }*/

    fun setupCarousel(viewPager: ViewPager2) {

        viewPager.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
        }
        viewPager.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(this, R.dimen.viewpager_current_item_horizontal_margin)
        viewPager.addItemDecoration(itemDecoration)

    }

 /*   fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions1) {
            result = ContextCompat.checkSelfPermission(requireContext(), p)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0])
        }
    }

    fun getLocationn() {
        if (checkPermissions()) {
            if (GPSEnabled()) {
                var locationManager =
                    requireContext().getSystemService(LOCATION_SERVICE) as LocationManager?

                var locationListener = object : LocationListener {

                    override fun onLocationChanged(location: Location) {
                        try {
                            var latitute = location!!.latitude
                            var longitute = location!!.longitude

                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val list: List<Address> =
                                geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            list[0].latitude
                            list[0].longitude
                            currentaddress.text = list[0].getAddressLine(0)
                                .split(",")[1] + "," + list[0].getAddressLine(0).split(",")[3]

                        } catch (exception: Exception) {
                            exception.toString()
                        }
                    }

                    override
                    fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                }

                try {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0f,
                        locationListener
                    )
                } catch (ex: SecurityException) {
                    Toast.makeText(
                        requireContext(),
                        "Error in getting address!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                GPSAlert()
            }
        }
    }

    fun GPSAlert() {
        val builder = AlertDialog.Builder(requireContext(), R.style.Theme_Transparent1).create()
        val view = layoutInflater.inflate(R.layout.gpsalert, null)

        var window = builder.window

        window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        var okay = view.findViewById<TextView>(R.id.accept)

        okay.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            builder.dismiss()
        }
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }*/

    fun getlegalassistanceloaderaler() {
        val builder = AlertDialog.Builder(context, R.style.Theme_Transparent1).create()
        val view = LayoutInflater.from(context).inflate(R.layout.timerclock, null)
        var cancel = view.findViewById<CardView>(R.id.backarrow)
        var choosemodelayout = view.findViewById<LinearLayout>(R.id.choosemodelayout)
        var continuewbutton = view.findViewById<TextView>(R.id.contbutton)
        var englistcharimage = view.findViewById<ImageView>(R.id.englishcharimage)
        var hindicharimage = view.findViewById<ImageView>(R.id.hindicharimage)
        var englisttxt = view.findViewById<TextView>(R.id.englisttxt)
        var hinditxt = view.findViewById<TextView>(R.id.hinditxt)
        var hindicard = view.findViewById<LinearLayout>(R.id.hindicard)
        var englishcard = view.findViewById<LinearLayout>(R.id.englishcard)

        var videoimage = view.findViewById<ImageView>(R.id.videoimage)
        var audioimage = view.findViewById<ImageView>(R.id.audioimage)
        var videotxt = view.findViewById<TextView>(R.id.videotxt)
        var audiotxt = view.findViewById<TextView>(R.id.audiotxt)
        var videocard = view.findViewById<LinearLayout>(R.id.videocard)
        var audiocard = view.findViewById<LinearLayout>(R.id.audiocard)

        choosemodelayout.visibility = View.VISIBLE

        cancel.setOnClickListener {
            builder.dismiss()
        }


        hindicard.setOnClickListener {
            hindicard.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            englishcard.setBackgroundResource(R.drawable.edittextback)
            englisttxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }


        englishcard.setOnClickListener {
            englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
            englisttxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            hindicard.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }

        videoimage.setOnClickListener {
            videocard.setBackgroundResource(R.drawable.selectlanguageborder)
            videotxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                videoimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            audiocard.setBackgroundResource(R.drawable.edittextback)
            audiotxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                audioimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }

        audiocard.setOnClickListener {
            audiocard.setBackgroundResource(R.drawable.selectlanguageborder)
            audiotxt.setTextColor(requireContext().getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                audioimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.red))
            )
            videocard.setBackgroundResource(R.drawable.edittextback)
            videotxt.setTextColor(requireContext().getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                videoimage,
                ColorStateList.valueOf(requireContext().getColor(R.color.black))
            )
        }

        continuewbutton.setOnClickListener {
            var intent = Intent(requireContext(), YourAdvocateActivity::class.java)
            startActivity(intent)
        }
        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun HitApiForServices() {
        shimmer.startShimmerAnimation()
        instancecard.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        viewModel.getServiceDataList(requireContext())
            .observe(requireActivity(), androidx.lifecycle.Observer { serviceData ->
               val response=serviceData.response
                if(!response.isNullOrEmpty()){
                val errorcode = serviceData.status
                if (errorcode == 200) {
                    serviceList = serviceData.response
                    if (!serviceList.isNullOrEmpty()) {
                        sharedpreferences!!.saveServicesData(serviceList)
                        onlysixserviceList.clear()
                        for (i in serviceList!!.indices) {
                            onlysixserviceList.add(serviceList!!.get(i))
                            if (i == 5) {
                                break
                            }
                        }
                        shimmer.stopShimmerAnimation()
                        instancecard.visibility = View.VISIBLE
                        shimmer.visibility = View.GONE
                        setInstanceServiceAdapter(onlysixserviceList)

                    } else {
                        shimmer.startShimmerAnimation()
                        instancecard.visibility = View.GONE
                        shimmer.visibility = View.VISIBLE
                    }
                } else {
                    shimmer.startShimmerAnimation()
                    instancecard.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                }

                    }
            })

        /*  var call=apiHolder.GetServices(Constant.HeadersWithAccess(accessToken))
          call.enqueue(object : retrofit2.Callback<ServicesDataModel>{
              override fun onResponse(call: Call<ServicesDataModel>?, response: Response<ServicesDataModel>?) {
                  if(response!!.isSuccessful){
                      var getdata= response.body()
                      var errorcode = getdata!!.status
                      if(errorcode==200){
                          serviceList = getdata.response
                          if(serviceList.size>0)
                          {

                          }
                      }
                      else{

                      }
                  }
              }

              override fun onFailure(call: Call<ServicesDataModel>?, t: Throwable?) {
                  shimmer.startShimmerAnimation()
                  instancecard.visibility=View.GONE
                  shimmer.visibility=View.VISIBLE
              }
          })*/

    }

    override fun onResume() {
        super.onResume()
        shimmer.startShimmerAnimation()
        bannerhandler.postDelayed(bannerrunnable,2000)
    }

    override fun onPause() {
        shimmer.stopShimmerAnimation()
        bannerhandler.removeCallbacks(bannerrunnable)
        super.onPause()

    }

}

class HorizontalMarginItemDecoration(home: Home, viewpagerCurrentItemHorizontalMargin: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int =
        home.resources.getDimension(viewpagerCurrentItemHorizontalMargin).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }


}


