package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.net.DnsResolver.Callback
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.ArrayMap
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.LoginResponseModel
import com.appsnipp.eadvokate.model.VerifyOtpResponseModel
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*

class VerifyOtpScreen : AppCompatActivity() {
    lateinit var backarrow:CardView
    lateinit var otp1:EditText
    lateinit var otp2:EditText
    lateinit var otp3:EditText
    lateinit var otp4:EditText
    lateinit var otp5:EditText
    lateinit var otp6:EditText
    lateinit var verifyotpbutton:TextView
    lateinit var resendOtptxt:TextView
    lateinit var contactNo:String
    lateinit var apiHolder: ApiHolder
    var sharedpreferences: Sharedpreferences? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadlocale()
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_verify_otp_screen)

        init()
        setclicklistner()
    }
    fun init(){
        if(intent.hasExtra("contactNo")){
            contactNo=intent.getStringExtra("contactNo").toString()
        }
        apiHolder= ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        backarrow=findViewById(R.id.backarrow)
        otp1=findViewById(R.id.opt1)
        otp2=findViewById(R.id.otp2)
        otp3=findViewById(R.id.otp3)
        otp4=findViewById(R.id.otp4)
        otp5=findViewById(R.id.otp5)
        otp6=findViewById(R.id.otp6)
        verifyotpbutton=findViewById(R.id.loginbutton)
        resendOtptxt=findViewById(R.id.resendOtptxt)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setclicklistner(){
        backarrow.setOnClickListener { 
            finish() 
        }
        resendOtptxt.setOnClickListener { 
            HitApiForResendOtp(contactNo)
        }

        verifyotpbutton.setOnClickListener {
            if(otp1.text.isNullOrEmpty()||otp2.text.isNullOrEmpty()||otp3.text.isNullOrEmpty()||otp4.text.isNullOrEmpty()||otp5.text.isNullOrEmpty()||otp6.text.isNullOrEmpty())
            {
              Toast.makeText(this@VerifyOtpScreen,"Please enter valid OTP !!",Toast.LENGTH_SHORT).show()
            }
            else{
                var OTP=StringBuilder().append(otp1.text.toString()).append(otp2.text.toString()).append(otp3.text.toString()).append(otp4.text.toString()).append(otp5.text.toString()).append(otp6.text.toString()).toString()
                HitApiForVerifyOtpAndLogin(OTP)
            }

        }

        otp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp1.getText().length == 1) {
                    otp2.requestFocus()
                    otp2.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp2.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)

                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)

                }

            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp2.getText().length == 1) {
                    otp3.requestFocus()
                    otp3.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp3.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)
                }
                else {
                    otp1.requestFocus()
                    otp1.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp1.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp2.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)

                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp3.getText().length == 1) {
                    otp4.requestFocus()
                    otp4.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp4.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)
                }
                else {
                    otp2.requestFocus()
                    otp2.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp2.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)

                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp4.getText().length == 1){
                    otp5.requestFocus()
                    otp5.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp5.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)
                }else{
                    otp3.requestFocus()
                    otp3.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp3.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp5.getText().length == 1){
                    otp6.requestFocus()
                    otp6.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp6.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                }else{
                    otp4.requestFocus()
                    otp4.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp4.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp6.setBackgroundResource(R.drawable.edittextback)

                    otp1.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                    otp6.setTextColor(Color.BLACK)
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })
        otp6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (otp6.getText().length == 1){
                    otp6.requestFocus()
                    otp6.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp6.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)
                    otp1.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                }else{
                    otp5.requestFocus()
                    otp5.setBackgroundResource(R.drawable.selectlanguageborder)
                    otp5.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
                    otp1.setBackgroundResource(R.drawable.edittextback)
                    otp2.setBackgroundResource(R.drawable.edittextback)
                    otp3.setBackgroundResource(R.drawable.edittextback)
                    otp4.setBackgroundResource(R.drawable.edittextback)
                    otp5.setBackgroundResource(R.drawable.edittextback)

                    otp1.setTextColor(Color.BLACK)
                    otp2.setTextColor(Color.BLACK)
                    otp3.setTextColor(Color.BLACK)
                    otp4.setTextColor(Color.BLACK)
                    otp5.setTextColor(Color.BLACK)
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    fun HitApiForVerifyOtpAndLogin(otp:String){
        Constant.loadingpopup(this@VerifyOtpScreen,"Verify Otp","",false)

        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("contact_number", contactNo)
        requestBody.put("otp", otp)

        val body: RequestBody = RequestBody.create(
           MediaType.parse( "application/json; charset=utf-8"), JSONObject(
                JSONObject(requestBody as MutableMap<*, *>).toString()
            ).toString()
        )
        val call = apiHolder.VerifyOtp(Constant.Headers(), body)
        call.enqueue(object : retrofit2.Callback<VerifyOtpResponseModel>{
            override fun onResponse(call: Call<VerifyOtpResponseModel>?, response: Response<VerifyOtpResponseModel>?) {
                Constant.StopLoader()
                if(response!!.isSuccessful){
                 var getdata=response.body()
                 var errorcode=getdata!!.status
                 if(errorcode==200){
                    var userInfo =getdata.response.user_info
                     sharedpreferences!!.setAccessToken("connect_sid",getdata.response.connect_sid)
                     sharedpreferences!!.setUserInfo("customer_id",userInfo.customer_id)
                     sharedpreferences!!.setUserInfo("name",userInfo.name)
                     sharedpreferences!!.setUserInfo("contact_number",userInfo.contact_number)
                     sharedpreferences!!.setUserInfo("email",userInfo.email)
                     sharedpreferences!!.setUserInfo("current_session",getdata.response.current_session)
                     sharedpreferences!!.islogged("Userlogin", true)
                     val intent=Intent(this@VerifyOtpScreen,MainActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                     startActivity(intent)
                   }
                }
                else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    val status=jsonObj.getString("status")
                    val message=jsonObj.getString("message")
                    if(status.equals("400")){
                        Toast.makeText(this@VerifyOtpScreen,"Incorrect Password !!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@VerifyOtpScreen,message,Toast.LENGTH_SHORT).show()
                    }
                }

            }
            override fun onFailure(call: Call<VerifyOtpResponseModel>?, t: Throwable?) {
                Constant.StopLoader()

            }

        })
    }

    fun HitApiForResendOtp(mobileNumber:String){
        Constant.loadingpopup(this@VerifyOtpScreen,"Resending OTP","",false)
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("contact_number", mobileNumber)

        val body: RequestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), JSONObject(
                JSONObject(requestBody as MutableMap<*, *>).toString()
            ).toString()
        )
        val call = apiHolder.Login(Constant.Headers(), body)

        call.enqueue(object : retrofit2.Callback<LoginResponseModel> {

            override fun onResponse(call: Call<LoginResponseModel>?, response: Response<LoginResponseModel>?) {
                Constant.StopLoader()
                if(response!!.isSuccessful){
                    var getdata=response.body()
                    var errorcode=getdata!!.status
                    if(errorcode==200){
                        Toast.makeText(this@VerifyOtpScreen,getdata.message,Toast.LENGTH_SHORT).show()

                    }else{

                        Toast.makeText(this@VerifyOtpScreen,getdata!!.message,Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    val status=jsonObj.getString("status")
                    val message=jsonObj.getString("message")
                    val response=jsonObj.getString("response")

                    Toast.makeText(this@VerifyOtpScreen,response,Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginResponseModel>?, t: Throwable?) {
                Constant.StopLoader()
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