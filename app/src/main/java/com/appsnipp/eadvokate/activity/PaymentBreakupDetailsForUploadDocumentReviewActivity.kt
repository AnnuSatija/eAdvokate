package com.appsnipp.eadvokate.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.GetReviewDocumentResponseModel
import com.appsnipp.eadvokate.multimedia.WebActivityForCCAvenuePayment
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.URI
import java.util.*

class PaymentBreakupDetailsForUploadDocumentReviewActivity : AppCompatActivity(){
    lateinit var backarrow: CardView
    lateinit var proceedtopay: TextView
    lateinit var totalpageNo: TextView
    lateinit var fee: TextView
    lateinit var cgst: TextView
    lateinit var gst: TextView
    lateinit var totalpayment: TextView
    lateinit var title: TextView
    var sharedpreferences: Sharedpreferences? = null
    var packagePosition:Int = 0
    var packageId:Int = 0
    var uploadFileUri :String ? = null
    lateinit var file:File
    lateinit var fileuri:Uri
    lateinit var apiHolder: ApiHolder
    var total_pages:String=""
    var price_per_page:String=""
    var price_before_gst:String=""
    var gstvalue:String=""
    var total:String=""
    var accessToken:String=""
    var orderId:String=""
    var hashToken:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_paymentbreakup_schedule_advocate)

        getIntentValue()
        init()
        setclicklistner()

    }

    fun getIntentValue(){
        if(intent.hasExtra("total_pages")){
            total_pages=intent.getIntExtra("total_pages",0).toString()
        }

        if(intent.hasExtra("price_per_page")){
            price_per_page=intent.getStringExtra("price_per_page")!!
        }

        if(intent.hasExtra("price_before_gst")){
            price_before_gst=intent.getStringExtra("price_before_gst")!!
        }

        if(intent.hasExtra("gst")){
            gstvalue=intent.getStringExtra("gst")!!
        }

        if(intent.hasExtra("total")){
            total=intent.getStringExtra("total")!!
        }
        if(intent.hasExtra("order_id")){
            orderId=intent.getStringExtra("order_id")!!
        }
        if(intent.hasExtra("order_hash")){
            hashToken=intent.getStringExtra("order_hash")!!
        }


    }

    fun init(){
        apiHolder= ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences= Sharedpreferences.getInstance(this@PaymentBreakupDetailsForUploadDocumentReviewActivity)
        title=findViewById(R.id.title)
        backarrow=findViewById(R.id.backarrow)
        proceedtopay=findViewById(R.id.proceedtopay)
        totalpageNo=findViewById(R.id.totalpageNo)
        fee=findViewById(R.id.fee)
        cgst=findViewById(R.id.cgst)
        gst=findViewById(R.id.gst)
        totalpayment=findViewById(R.id.totalpayment)

        title.text=applicationContext.getString(R.string.paymentbreak)
        proceedtopay.text=applicationContext.getString(R.string.pros)

        totalpageNo.text=total_pages
        fee.text="₹"+price_per_page
        cgst.text="₹"+price_before_gst
        gst.text="₹"+gstvalue
        totalpayment.text="₹"+total


    }

    fun setclicklistner() {

        backarrow.setOnClickListener {
            finish()
        }

        proceedtopay.setOnClickListener {
            val intent=Intent(this@PaymentBreakupDetailsForUploadDocumentReviewActivity,WebActivityForCCAvenuePayment::class.java)
            intent.putExtra("order_hash",hashToken)
            startActivity(intent)

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

}