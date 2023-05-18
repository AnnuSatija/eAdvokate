package com.appsnipp.eadvokate.view.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.AllPaymentAdapter
import com.appsnipp.eadvokate.adapter.PendingPaymentAdapter
import com.appsnipp.eadvokate.model.AllPaymentResponse
import com.appsnipp.eadvokate.model.RequestPaymentModel
import com.appsnipp.eadvokate.model.RequestResponse
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass
import retrofit2.Call
import retrofit2.Response
import java.util.*

class PaymentRefundActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit var pendingRecyclerview:RecyclerView
    lateinit var completepaymentRecyclerview:RecyclerView
    lateinit var accessToken:String
    lateinit var apiHolder: ApiHolder
    lateinit var paymentPendingList:MutableList<RequestResponse>
    lateinit var pendingadapter:PendingPaymentAdapter
    lateinit var allPaymentAdapter:AllPaymentAdapter
    lateinit var pendinglayout:LinearLayout
    lateinit var pendingtxt:TextView
    lateinit var completelayout:LinearLayout
    lateinit var completetxt:TextView
    lateinit var notfoundlayout:RelativeLayout
    lateinit var notfoundlayout1:RelativeLayout
    lateinit var PendingLayout:RelativeLayout
    lateinit var CompletedLayout:RelativeLayout
    lateinit var viewModel:ViewModelClass
    var allPaymentList:MutableList<AllPaymentResponse>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_payment_refund)

        init()
        setclicklistner()
        HitApiForPaymentPending()

    }

    fun  init(){
        viewModel=ViewModelProvider(this)[ViewModelClass::class.java]
        apiHolder=ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences= Sharedpreferences.getInstance(this)
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        backarrow=findViewById(R.id.backarrow)
        pendingRecyclerview=findViewById(R.id.pendingpayment)
        pendingRecyclerview.layoutManager=LinearLayoutManager(this)
        completepaymentRecyclerview=findViewById(R.id.completepayment)
        completepaymentRecyclerview.layoutManager=LinearLayoutManager(this)
        pendinglayout=findViewById(R.id.pendinglayout)
        pendingtxt=findViewById(R.id.pendingtxt)
        completelayout=findViewById(R.id.completelayout)
        completetxt=findViewById(R.id.completetxt)
        notfoundlayout=findViewById(R.id.notfoundlayout)
        notfoundlayout1=findViewById(R.id.notfoundlayout1)
        PendingLayout=findViewById(R.id.PendingLayout)
        CompletedLayout=findViewById(R.id.CompletedLayout)
        PendingLayout.visibility=View.VISIBLE
        CompletedLayout.visibility=View.GONE

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }

        pendinglayout.setOnClickListener{
            pendinglayout.setBackgroundResource(R.drawable.signbutton)
            pendingtxt.setTextColor(applicationContext.getColor(R.color.white))
            completelayout.setBackgroundResource(R.drawable.selectlanguageborder)
            completetxt.setTextColor(applicationContext.getColor(R.color.red))
            PendingLayout.visibility=View.VISIBLE
            CompletedLayout.visibility=View.GONE
            HitApiForPaymentPending()

        }

        completelayout.setOnClickListener {
            completelayout.setBackgroundResource(R.drawable.signbutton)
            completetxt.setTextColor(applicationContext.getColor(R.color.white))
            pendinglayout.setBackgroundResource(R.drawable.selectlanguageborder)
            pendingtxt.setTextColor(applicationContext.getColor(R.color.red))
            PendingLayout.visibility=View.GONE
            CompletedLayout.visibility=View.VISIBLE
            setAllPaymentAdapter()
        }

    }

    fun HitApiForPaymentPending(){
        Constant.loadingpopup(this@PaymentRefundActivity,"Loading data","",false)
        var call=apiHolder.RequestPayment(Constant.HeadersWithAccess(accessToken))
        call.enqueue(object :retrofit2.Callback<RequestPaymentModel>{

            override fun onResponse(call: Call<RequestPaymentModel>, response: Response<RequestPaymentModel>) {
                Constant.StopLoader()
                if(response.isSuccessful){
                    var getdata=response.body()
                    var errorcode=getdata!!.status
                    if(errorcode==200){
                        paymentPendingList=getdata.response
                        if(paymentPendingList.size>0){
                            notfoundlayout.visibility=View.GONE
                            pendingRecyclerview.visibility=View.VISIBLE
                            setPendingAdapter(paymentPendingList)
                        }
                        else{
                            pendingRecyclerview.visibility=View.GONE
                            notfoundlayout.visibility=View.VISIBLE
                        }
                    }

                }
                else{
                    pendingRecyclerview.visibility=View.GONE
                    notfoundlayout.visibility=View.VISIBLE
                }

            }

            override fun onFailure(call: Call<RequestPaymentModel>, t: Throwable) {
                Constant.StopLoader()
                pendingRecyclerview.visibility=View.GONE
                notfoundlayout.visibility=View.VISIBLE
            }

        })

    }

    fun setPendingAdapter(pendingLiist:MutableList<RequestResponse>){
        pendingadapter=PendingPaymentAdapter(this,pendingLiist)
        pendingRecyclerview.adapter=pendingadapter
        pendingadapter.notifyDataSetChanged()
    }

    fun setAllPaymentAdapter(){
        Constant.loadingpopup(this@PaymentRefundActivity,"Loading","",false)
        viewModel.getAllPaymentList(this@PaymentRefundActivity).observe(this, androidx.lifecycle.Observer { allpaymentList->
            Constant.StopLoader()
            val errorcode=allpaymentList.status
             if(errorcode==200){
                 allPaymentList=allpaymentList.response
                 if(!allPaymentList.isNullOrEmpty()) {
                     completepaymentRecyclerview.visibility=View.VISIBLE
                     notfoundlayout1.visibility=View.GONE
                     allPaymentAdapter = AllPaymentAdapter(this@PaymentRefundActivity, allPaymentList)
                     completepaymentRecyclerview.adapter = allPaymentAdapter
                     allPaymentAdapter.notifyDataSetChanged()
                 }else{
                     completepaymentRecyclerview.visibility=View.GONE
                     notfoundlayout1.visibility=View.VISIBLE
                 }

             }else{
                 completepaymentRecyclerview.visibility=View.GONE
                 notfoundlayout1.visibility=View.VISIBLE
             }

        })
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