package com.appsnipp.eadvokate.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.*
import com.appsnipp.eadvokate.adapter.HomaPageViewPagerAdapter
import com.appsnipp.eadvokate.adapter.ImageAdpter
import com.appsnipp.eadvokate.adapter.InstanceServiceviewallAdapter
import com.appsnipp.eadvokate.adapter.WhatsissueServiceOnlyNameAdapter
import com.appsnipp.eadvokate.model.BannerResponse
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.InstanceServiceOnlyNameModel
import com.appsnipp.eadvokate.model.ServicesResponse
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass

class Explore : Fragment() {
    lateinit var viewpager: ViewPager2
    lateinit var instanceservicesrecyclerview: RecyclerView
    lateinit var trendingadvocaterecyclerview: RecyclerView
    lateinit var whatsissueimage: ImageView
    lateinit var viewallinstance: TextView
    lateinit var instanceServiceAdapter: InstanceServiceviewallAdapter
    val imagelist= arrayOf(R.drawable.explorebannericon)
    lateinit var sharedpreferences:Sharedpreferences
    lateinit var servicelist:MutableList<ServicesResponse>
    var sixservicelist:MutableList<ServicesResponse> = arrayListOf()
    lateinit var videocalllayout: CardView
    lateinit var audiocalllayout: CardView
    var dashboardBannerList: MutableList<BannerResponse>? = mutableListOf()
    lateinit var viewModel: ViewModelClass
    lateinit var bannerhandler:android.os.Handler
    lateinit var bannerrunnable:Runnable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_explore, container, false)
        init(view)
        if(sharedpreferences.getServicesData().size>0){
            servicelist=sharedpreferences.getServicesData()
        }
        sixservicelist.clear()
        for(i in servicelist.indices) {
            sixservicelist.add(servicelist.get(i))
            if(i==5){
                break
            }
        }
        setInstanceServiceAdapter(sixservicelist)
        setTrendingAdvocateAdapter(Constant.trendingadvocate(requireContext()))
        clicklistner()
        return view
    }

    fun init(view: View){
        sharedpreferences= Sharedpreferences.getInstance(requireContext())
        dashboardBannerList=sharedpreferences.getDashboardData()
        viewpager=view.findViewById(R.id.view_pager)
        instanceservicesrecyclerview=view.findViewById(R.id.instanceservicesview)
        trendingadvocaterecyclerview=view.findViewById(R.id.trendingadvocate)
        whatsissueimage=view.findViewById(R.id.whatsissueimage)
        viewallinstance=view.findViewById(R.id.viewallinstance)
        videocalllayout=view.findViewById(R.id.videocalllayout)
        audiocalllayout=view.findViewById(R.id.audiocalllayout)
        instanceservicesrecyclerview.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        trendingadvocaterecyclerview.layoutManager=LinearLayoutManager(requireContext())
        setupCarousel(viewpager)
        val adapter = HomaPageViewPagerAdapter(viewpager,requireActivity(), dashboardBannerList!!)
        viewpager.adapter = adapter
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

    fun clicklistner(){

        viewallinstance.setOnClickListener {
            var intent= Intent(requireContext(), InstantServicesViewAllActivity::class.java)
            intent.putExtra("Check","ViewAll")
            startActivity(intent)
        }

        whatsissueimage.setOnClickListener {
            var intent= Intent(requireContext(), WhatsissueViewAllActivity::class.java)
            startActivity(intent)
        }

        videocalllayout.setOnClickListener {
            var intent= Intent(requireContext(),InstantServicesViewAllActivity::class.java)
            intent.putExtra("type","Video")
            startActivity(intent)
        }

        audiocalllayout.setOnClickListener {
            var intent= Intent(requireContext(),InstantServicesViewAllActivity::class.java)
            intent.putExtra("type","Audio")
            startActivity(intent)
        }

    }

    fun setTrendingAdvocateAdapter(list:List<InstanceServiceModel>){
        instanceServiceAdapter= InstanceServiceviewallAdapter(requireActivity(),arrayListOf(),list,"trendadvocate")
        trendingadvocaterecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()
    }

    fun setInstanceServiceAdapter(list:MutableList<ServicesResponse>){
        instanceServiceAdapter= InstanceServiceviewallAdapter(requireActivity(),list,arrayListOf(),"instance")
        instanceservicesrecyclerview.adapter=instanceServiceAdapter
        instanceServiceAdapter.notifyDataSetChanged()
    }

    fun setupCarousel(viewPager: ViewPager2) {

        viewPager.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
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

    class HorizontalMarginItemDecoration(explore: Explore, viewpagerCurrentItemHorizontalMargin: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            explore.resources.getDimension(viewpagerCurrentItemHorizontalMargin).toInt()

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


}