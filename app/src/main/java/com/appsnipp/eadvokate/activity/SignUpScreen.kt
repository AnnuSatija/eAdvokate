package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import android.util.Patterns
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.SignupResponseModel
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


class SignUpScreen : AppCompatActivity() {
    lateinit var signintxt:TextView
    lateinit var registrationtxt:TextView
    var sharedpreferences: Sharedpreferences? = null
    lateinit var hinditxt:TextView
    lateinit var englishtxt:TextView
    lateinit var term:TextView
    lateinit var privacypolicy:TextView
    lateinit var username:EditText
    lateinit var mobileno:EditText
    lateinit var emailid:EditText
    lateinit var policycheck:CheckBox
    var language:String =""
    lateinit var apiHolder: ApiHolder


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_sign_up_screen)

        init()
        setclicklistner()

    }

    fun init(){
        sharedpreferences= Sharedpreferences.getInstance(this)
        apiHolder= ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences?.setlanguage("MyLanguage", "en")
        signintxt=findViewById(R.id.signup)
        registrationtxt=findViewById(R.id.registrationtxt)
        englishtxt=findViewById(R.id.englishtxt)
        hinditxt=findViewById(R.id.hinditxt)
        username=findViewById(R.id.username)
        emailid=findViewById(R.id.emailid)
        mobileno=findViewById(R.id.mobileno)
        policycheck=findViewById(R.id.policycheck)
        term=findViewById(R.id.term)
        privacypolicy=findViewById(R.id.privacypolicy)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setclicklistner(){

        signintxt.setOnClickListener {
            val intent = Intent(this@SignUpScreen, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        privacypolicy.setOnClickListener {
            val url = "https://eadvokate.com/privacy-policies"
            val i =  Intent(this@SignUpScreen, OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Privacy Policy")
            i.putExtra("account","account")
            startActivity(i)
        }

        term.setOnClickListener {
            val url = "https://eadvokate.com/terms-and-conditions";
            val i =  Intent(this@SignUpScreen,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Terms and Conditions")
            i.putExtra("account","account")
            startActivity(i)
        }

        registrationtxt.setOnClickListener {
            if(username.text.isNullOrEmpty()||mobileno.text.isNullOrEmpty()||mobileno.text.length<10||emailid.text.isNullOrEmpty()||!isValidEmail(emailid.text)){
                if(username.text.isNullOrEmpty()){
                    username.error="Enter user name !!"
                    username.requestFocus()
                    return@setOnClickListener
                }
                if(mobileno.text.isNullOrEmpty()){
                    mobileno.error="Enter mobile number !!"
                    mobileno.requestFocus()
                    return@setOnClickListener
                }
                if(mobileno.text.length<10){
                    mobileno.error="Enter valid mobile number !!"
                    mobileno.requestFocus()
                    return@setOnClickListener
                }

                if(emailid.text.isNullOrEmpty()){
                    emailid.error="Enter email id !!"
                    emailid.requestFocus()
                    return@setOnClickListener
                }

                if(!isValidEmail(emailid.text)){
                    emailid.error="Enter valid email id !!"
                    emailid.requestFocus()
                    return@setOnClickListener
                }
            }
            else{
                if(!language.isNullOrEmpty()){
                    if(policycheck.isChecked){
                        HitApiForRegistration()
                    }
                    else{
                        Toast.makeText(this@SignUpScreen,resources.getString(R.string.policymessage),Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@SignUpScreen,"Please select language first !!",Toast.LENGTH_SHORT).show()
                }
            }

        }

        englishtxt.setOnClickListener {
            englishtxt.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setBackgroundResource(R.drawable.edittextback)
            englishtxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            hinditxt.setTextColor(Color.BLACK)
            language="English"
            setLocale("en")

        }

        hinditxt.setOnClickListener {
            hinditxt.setBackgroundResource(R.drawable.selectlanguageborder)
            englishtxt.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            englishtxt.setTextColor(Color.BLACK)
            language="Hindi"
            setLocale("hi")

        }

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun HitApiForRegistration(){
        Constant.loadingpopup(this,"Signup","",false)
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("name", username.text.toString())
        requestBody.put("contact_number", mobileno.text.toString())
        requestBody.put("email", emailid.text.toString())
        requestBody.put("consultancy_language", language)

        val body: RequestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), JSONObject(
                JSONObject(requestBody as MutableMap<*, *>).toString()
            ).toString()
        )
        val call = apiHolder.SignUp(Constant.Headers(), body)
        call.enqueue(object : Callback<SignupResponseModel>{
            override fun onResponse(call: Call<SignupResponseModel>?, response: Response<SignupResponseModel>?)
            {
                Constant.StopLoader()
                try {
                    if (response!!.isSuccessful) {
                        var getdata = response.body()
                        var errordata = getdata!!.status
                        if (errordata == 200) {
                            Toast.makeText(
                                this@SignUpScreen,
                                "Signup Successfully Done!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@SignUpScreen, SignInActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        val status = jsonObj.getString("status")
                        val message = jsonObj.getString("message")
                        val response = jsonObj.getString("response")
                        mobileno.text.clear()
                        Toast.makeText(this@SignUpScreen, response, Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e:Exception){
                    e.message
                }
            }
            override fun onFailure(call: Call<SignupResponseModel>?, t: Throwable?) {
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


}