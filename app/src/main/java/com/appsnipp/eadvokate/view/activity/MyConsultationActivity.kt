package com.appsnipp.eadvokate.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsnipp.eadvokate.adapter.MyConsulationAdapter
import com.appsnipp.eadvokate.model.ConsulationResponse
import com.appsnipp.eadvokate.viewmodel.ViewModelClass
import com.appsnipp.eadvokate.databinding.ActivityMyConsultationBinding;
import com.appsnipp.eadvokate.utils.Constant

class MyConsultationActivity : AppCompatActivity() {
    lateinit var context:Context
    var consultationList:MutableList<ConsulationResponse> = arrayListOf()
    lateinit var viewModel:ViewModelClass
    lateinit var consulationAdapter:MyConsulationAdapter
    lateinit var binding:ActivityMyConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context=this@MyConsultationActivity

        callViewModel()
        callListner()
    }

    fun callListner(){
        binding.backarrow.setOnClickListener {
            finish()
        }
    }

    fun callViewModel(){
        viewModel=ViewModelProvider(this)[ViewModelClass::class.java]
        Constant.loadingpopup(this@MyConsultationActivity,"loading","",false)
        viewModel.getConsultationList(this@MyConsultationActivity)!!.observe(this, Observer {consuldata->
            Constant.StopLoader()
            consultationList=consuldata.response
            var errorcode=consuldata.status
            if(errorcode==200) {
                if (consultationList.size != null) {
                    binding.datanotfound.visibility= View.GONE
                    binding.consulationview.visibility=View.VISIBLE
                    consulationAdapter = MyConsulationAdapter(this@MyConsultationActivity, consultationList)
                    binding.consulationview.layoutManager = LinearLayoutManager(this)
                    binding.consulationview.adapter = consulationAdapter
                    consulationAdapter.notifyDataSetChanged()

                }
                else{
                    binding.datanotfound.visibility= View.VISIBLE
                    binding.consulationview.visibility=View.GONE
                }
            }
            else{
                binding.datanotfound.visibility= View.VISIBLE
                binding.consulationview.visibility=View.GONE
            }

        })
    }


}