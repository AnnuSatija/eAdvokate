package com.appsnipp.eadvokate.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.InstantServiceCasesAdapter
import com.appsnipp.eadvokate.adapter.ReviewDocAdapter
import com.appsnipp.eadvokate.databinding.ActivityMyCasesBinding
import com.appsnipp.eadvokate.model.InstantServiceResponse
import com.appsnipp.eadvokate.model.ReviewDocResponse
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.viewmodel.ViewModelClass

class MyCases : AppCompatActivity() {
    lateinit var binding:ActivityMyCasesBinding
    lateinit var viewModel:ViewModelClass
    var ReviewDocList:MutableList<ReviewDocResponse>?=null
    var InstantServiceList:MutableList<InstantServiceResponse>?=null
    lateinit var reviewAdapter:ReviewDocAdapter
    lateinit var instantserviceAdapter:InstantServiceCasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyCasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=ViewModelProvider(this)[ViewModelClass::class.java]
        clicklistner()
        setReviewDocumentDataOnUi()

    }

    fun setReviewDocumentDataOnUi(){
        Constant.loadingpopup(this@MyCases,"loading","",false)
        viewModel.getReviewDocList(this@MyCases).observe(this, Observer { reviewDoc->
            val errorcode=reviewDoc.status
            ReviewDocList=reviewDoc.response
            if(errorcode==200){
                Constant.StopLoader()
                if(!ReviewDocList.isNullOrEmpty()){
                    binding.reviewdocumentrecyclerview.visibility=View.VISIBLE
                    binding.notfoundlayout.visibility=View.GONE
                    binding.reviewdocumentrecyclerview.layoutManager=LinearLayoutManager(this@MyCases)
                    reviewAdapter= ReviewDocAdapter(this@MyCases,ReviewDocList)
                    binding.reviewdocumentrecyclerview.adapter=reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                }
                else{
                    binding.reviewdocumentrecyclerview.visibility=View.GONE
                    binding.notfoundlayout.visibility=View.VISIBLE
                }
            }
            else{
                binding.reviewdocumentrecyclerview.visibility=View.GONE
                binding.notfoundlayout.visibility=View.VISIBLE
            }
        })
    }

    fun setInstantServiceDataOnUi(){
        Constant.loadingpopup(this@MyCases,"loading","",false)
        viewModel.getInstantServiceList(this@MyCases).observe(this, Observer { instantservice->
            val errorcode=instantservice.status
            InstantServiceList=instantservice.response
            if(errorcode==200){
                Constant.StopLoader()
                if(!InstantServiceList.isNullOrEmpty()){
                    binding.instanceservicerecyclerview.visibility=View.VISIBLE
                    binding.notfoundlayout1.visibility=View.GONE
                    binding.instanceservicerecyclerview.layoutManager=LinearLayoutManager(this@MyCases)
                    instantserviceAdapter= InstantServiceCasesAdapter(this@MyCases,InstantServiceList)
                    binding.instanceservicerecyclerview.adapter=instantserviceAdapter
                    instantserviceAdapter.notifyDataSetChanged()
                }
                else{
                    binding.instanceservicerecyclerview.visibility=View.GONE
                    binding.notfoundlayout1.visibility=View.VISIBLE
                }
            }
            else{
                binding.instanceservicerecyclerview.visibility=View.GONE
                binding.notfoundlayout1.visibility=View.VISIBLE
            }
        })

    }


    fun clicklistner(){
        binding.reviewdoclayout.setOnClickListener {
            binding.reviewdocumentlayout.visibility=View.VISIBLE
            binding.instanceserviceslayout.visibility=View.GONE
            binding.reviewdoclayout.setBackgroundResource(R.drawable.signbutton)
            binding.instantServiceLayout.setBackgroundResource(R.drawable.selectlanguageborder)
            binding.servicetxt.setTextColor(applicationContext.getColor(R.color.red))
            binding.reviewtxt.setTextColor(applicationContext.getColor(R.color.white))
            setReviewDocumentDataOnUi()

        }

        binding.instantServiceLayout.setOnClickListener {
            binding.reviewdocumentlayout.visibility=View.GONE
            binding.instanceserviceslayout.visibility=View.VISIBLE
            binding.reviewdoclayout.setBackgroundResource(R.drawable.selectlanguageborder)
            binding.instantServiceLayout.setBackgroundResource(R.drawable.signbutton)
            binding.servicetxt.setTextColor(applicationContext.getColor(R.color.white))
            binding.reviewtxt.setTextColor(applicationContext.getColor(R.color.red))
            setInstantServiceDataOnUi()
        }


        binding.backarrow.setOnClickListener {
            finish()
        }


    }


}