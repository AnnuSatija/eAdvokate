package com.appsnipp.eadvokate.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.LoginResponseModel
import com.appsnipp.eadvokate.multimedia.OpenMedia
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignInActivity : AppCompatActivity() {
    lateinit var signuptext:TextView
    lateinit var loginbutton:TextView
    lateinit var hinditxt:TextView
    lateinit var englishtxt:TextView
    lateinit var mobileno:EditText
    val language:String=""
    var keepchange = false
    var sharedpreferences: Sharedpreferences? = null
    lateinit var apiHolder:ApiHolder
    lateinit var policycheck: CheckBox
    lateinit var term:TextView
    lateinit var privacypolicy:TextView

  /*  var permissions1 = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.ACCESS_WIFI_STATE
    )*/

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_sign_in)

        init()
        setclicklistner()
    }

   fun init(){
       sharedpreferences= Sharedpreferences.getInstance(this)
       apiHolder=ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
       sharedpreferences?.setlanguage("MyLanguage", "en")
       signuptext=findViewById(R.id.signup);
       loginbutton=findViewById(R.id.loginbutton)
       englishtxt=findViewById(R.id.englishtxt)
       hinditxt=findViewById(R.id.hinditxt)
       mobileno=findViewById(R.id.mobileno)
       policycheck=findViewById(R.id.policycheck)
       term=findViewById(R.id.term)
       privacypolicy=findViewById(R.id.privacypolicy)

   }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setclicklistner(){
        privacypolicy.setOnClickListener {
            val url = "https://eadvokate.com/privacy-policies"
            val i =  Intent(this@SignInActivity, OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Privacy Policy")
            i.putExtra("account","account")
            startActivity(i)
        }

        term.setOnClickListener {
            val url = "https://eadvokate.com/terms-and-conditions";
            val i =  Intent(this@SignInActivity,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Terms and Conditions")
            i.putExtra("account","account")
            startActivity(i)
        }

        signuptext.setOnClickListener {
                val intent = Intent(this@SignInActivity, SignUpScreen::class.java)
                startActivity(intent)
                finish()

        }
        loginbutton.setOnClickListener {
            if(mobileno.text.isNullOrBlank()||mobileno.text.isNullOrEmpty()){
                mobileno.error= " Please enter mobile number !!"
                return@setOnClickListener
            }
            else{
                if(policycheck.isChecked){
                    HitApiForLogin(mobileno.text.toString())
                }
                else{
                    Toast.makeText(this@SignInActivity,resources.getString(R.string.policymessage),Toast.LENGTH_SHORT).show()
                }

           }

        }

        englishtxt.setOnClickListener {
            englishtxt.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setBackgroundResource(R.drawable.edittextback)
            englishtxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            hinditxt.setTextColor(Color.BLACK)
            setLocale("en")

        }


        hinditxt.setOnClickListener {
            hinditxt.setBackgroundResource(R.drawable.selectlanguageborder)
            englishtxt.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            englishtxt.setTextColor(Color.BLACK)
            setLocale("hi")

        }

    }
    fun HitApiForLogin(mobileNumber:String){
        Constant.loadingpopup(this@SignInActivity,"Login","",false)
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("contact_number", mobileNumber)

        val body: RequestBody = RequestBody.create(
           MediaType.parse( "application/json; charset=utf-8"), JSONObject(
                JSONObject(requestBody as MutableMap<*, *>).toString()
            ).toString()
        )
        val call = apiHolder.Login(Constant.Headers(), body)

        call.enqueue(object :Callback<LoginResponseModel>{

            override fun onResponse(call: Call<LoginResponseModel>?, response: Response<LoginResponseModel>?) {
                Constant.StopLoader()
                if(response!!.isSuccessful){
                  var getdata=response.body()
                  var errorcode=getdata!!.status
                  if(errorcode==200){
                      Toast.makeText(this@SignInActivity,getdata.message,Toast.LENGTH_SHORT).show()
                      val intent = Intent(this@SignInActivity, VerifyOtpScreen::class.java)
                      intent.putExtra("contactNo",mobileno.text.toString())
                      startActivity(intent)
                      finish()
                  }else{
                      mobileno.text.clear()
                      Toast.makeText(this@SignInActivity,getdata.message,Toast.LENGTH_SHORT).show()
                  }
                }
                else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    val status=jsonObj.getString("status")
                    val message=jsonObj.getString("message")
                    val response=jsonObj.getString("response")
                    mobileno.text.clear()
                    Toast.makeText(this@SignInActivity,response,Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginResponseModel>?, t: Throwable?) {
                Constant.StopLoader()
            }

        })

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
        sharedpreferences?.setlanguage("MyLanguage", lang)
    }

    /*private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions1) {
            result = ContextCompat.checkSelfPermission(this@SignInActivity, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this@SignInActivity, listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0])
            //resume tasks needing this permission
            HitApiForLogin(mobileno.text.toString())
        }
    }
*/

}