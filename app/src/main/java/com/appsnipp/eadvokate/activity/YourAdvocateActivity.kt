package com.appsnipp.eadvokate.activity

import android.Manifest
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.fragment.FeedbackVideocall
import com.appsnipp.eadvokate.model.JoinMeetingModel
import com.appsnipp.eadvokate.multimedia.WebViewForVideoMeeting
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class YourAdvocateActivity : AppCompatActivity() {
    lateinit var backarrow: CardView
    var sharedpreferences: Sharedpreferences? = null
    lateinit var viewprofile: TextView
    lateinit var advocatename: TextView
    lateinit var textforcontinuew: TextView
    lateinit var exp: TextView
    lateinit var advocatedescription: TextView
    lateinit var contibutton: LinearLayout
    lateinit var joinbutton: LinearLayout
    lateinit var title: TextView
    lateinit var advocatelayout: RelativeLayout
    lateinit var paymentbreakuplayout: RelativeLayout
    var chekvisibility= false
    lateinit  var advocateName:String
    lateinit  var Description:String
    lateinit  var Experience:String
    lateinit  var Photo:String
    lateinit  var consultantmode:String
    lateinit  var roomId:String
    lateinit  var meetingId:String
    lateinit  var paymentdetails:String
    lateinit  var advocateimage:ImageView
    lateinit  var imageicon:ImageView
    lateinit  var contubutton:LinearLayout
    lateinit  var layoutforAudio:LinearLayout
    lateinit  var layoutforVideo:LinearLayout
    lateinit var apiHolder: ApiHolder
    var accessToken:String=""
    var CheckIntentBrowser=false

    private val permission = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.MODIFY_AUDIO_SETTINGS)

    private val requestCode = 1
    val bundle=Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_your_advocate)

        getIntentValue()
        init()
        setDataForui()
        setclicklistner()
    }

    fun getIntentValue(){

        if(intent!!.hasExtra("name")){
            advocateName=intent.getStringExtra("name").toString()
        }
        if(intent!!.hasExtra("description")){
            Description=intent.getStringExtra("description").toString()
        }
        if(intent!!.hasExtra("experience")){
            Experience=intent.getStringExtra("experience").toString()
        }
        if(intent!!.hasExtra("photo")){
            Photo=intent.getStringExtra("photo").toString()
        }
        if(intent!!.hasExtra("mode")){
            consultantmode=intent.getStringExtra("mode").toString()
        }
        if(intent!!.hasExtra("room_id")){
            roomId=intent.getStringExtra("room_id").toString()
        }
        if(intent!!.hasExtra("meeting_id")){
            meetingId=intent.getStringExtra("meeting_id").toString()
        }
        if(intent!!.hasExtra("paymentdetailsfirst")){
            paymentdetails=intent.getStringExtra("paymentdetailsfirst").toString()
        }

    }

    fun init(){
        sharedpreferences= Sharedpreferences.getInstance(this)
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        meetingId = sharedpreferences!!.getData("meetingId")
        apiHolder=ApiClient.getApiClient(Constant.Authusername, resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        backarrow=findViewById(R.id.backarrow)
        contibutton=findViewById(R.id.contubutton)
        joinbutton=findViewById(R.id.joinbutton)
        title=findViewById(R.id.title)
        advocatelayout=findViewById(R.id.advocatelayout)
        paymentbreakuplayout=findViewById(R.id.paymentbreakuplayout)
        advocateimage=findViewById(R.id.image)
        imageicon=findViewById(R.id.imageicon)
        advocatename=findViewById(R.id.advocatename)
        layoutforAudio=findViewById(R.id.layoutforAudio)
        layoutforVideo=findViewById(R.id.layoutforVideo)
        exp=findViewById(R.id.exp)
        contubutton=findViewById(R.id.contubutton)
        advocatedescription=findViewById(R.id.advocatedescription)
        textforcontinuew=findViewById(R.id.textforcontinuew)


        if(paymentdetails.equals("payment")){
            advocatelayout.visibility=View.GONE
            paymentbreakuplayout.visibility=View.VISIBLE
            title.text = "Payment Details"
        }
        else{
            advocatelayout.visibility=View.VISIBLE
            paymentbreakuplayout.visibility=View.GONE
            title.text = "Your Advocate"
        }

    }

    override fun onStart() {
        super.onStart()
        if(CheckIntentBrowser){
            advocatelayout.visibility=View.GONE
            title.text = "Feedback"
            backarrow.visibility = View.GONE
            advocatelayout.visibility=View.GONE
            val feedbackfragment = FeedbackVideocall()
            bundle.putString("roomid",roomId)
            feedbackfragment.arguments=bundle
            setCurrentFragment(feedbackfragment)
        }
        else{
            if(paymentdetails.equals("payment")){
                advocatelayout.visibility=View.GONE
                paymentbreakuplayout.visibility=View.VISIBLE
                title.text = "Payment Details"
            }
            else{
                advocatelayout.visibility=View.VISIBLE
                paymentbreakuplayout.visibility=View.GONE
                title.text=applicationContext.getString(R.string.youradvocate)
            }
        }
    }

    fun setDataForui(){
        advocatename.text="Adv."+advocateName
        if(Experience.isNullOrBlank()){
            exp.text="Experience-"
        }else{
            exp.text="Experience-"+Experience+"yrs"
        }
        Glide.with(this).load(Photo).error(R.drawable.demoimageadvocate).into(advocateimage)
        advocatedescription.text=Description
        if(consultantmode=="Video"){
            layoutforVideo.visibility=View.VISIBLE
            layoutforAudio.visibility=View.GONE
            textforcontinuew.text=  "Join Now"
           imageicon.setImageResource(getResources().getIdentifier("instancevideocall", "drawable", this.getPackageName()))
        }
        else{
            layoutforVideo.visibility=View.GONE
            layoutforAudio.visibility=View.VISIBLE
          /*  textforcontinuew.text="Connect Now"
            imageicon.setImageResource(getResources().getIdentifier("instanceaudiocall", "drawable", this.getPackageName()))
        */



        }

    }

    fun setclicklistner() {

        backarrow.setOnClickListener {
            val fragment=supportFragmentManager.findFragmentByTag("feedbackfragment")
            if (fragment is FeedbackVideocall) {
                var intent=Intent(this@YourAdvocateActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                if(chekvisibility)
                {
                    advocatelayout.visibility=View.VISIBLE
                    paymentbreakuplayout.visibility=View.GONE
                    chekvisibility=false
                    title.text=applicationContext.getString(R.string.youradvocate)
                }
                else{
                    finish()
                }

            }
        }

        contibutton!!.setOnClickListener {
            advocatelayout.visibility=View.GONE
            paymentbreakuplayout.visibility=View.VISIBLE
            chekvisibility=true
            title.text=""

        }

        joinbutton.setOnClickListener{
            paymentdetails=""
            advocatelayout.visibility=View.VISIBLE
            paymentbreakuplayout.visibility=View.GONE
            title.text=applicationContext.getString(R.string.youradvocate)

        }

           // for 5 minutes.............................................................
        contubutton.isEnabled=true
        contubutton.alpha=1f

        var timer = object : CountDownTimer(300000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("TimerStart" ,(millisUntilFinished / 1000).toString())
               var timerr=(millisUntilFinished / 1000).toString().trim()
                Log.d("disableTime",timerr)
            }
            override fun onFinish() {
                contibutton.isEnabled=false
                contubutton.alpha=.5f
            }
        }.start()

        contibutton.setOnClickListener {
            if(!meetingId.isNullOrBlank()){
                hitApiForJoinMeeting()
            }
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


    fun hitApiForJoinMeeting(){
        Constant.loadingpopup(this,"Please note this call will be recorded for quality assurance and training purposes","",false)
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("meeting_id", meetingId)
        val body: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject(requestBody.toString()).toString())

        var call=apiHolder.JoinMeeting(Constant.HeadersWithAccess(accessToken),body)

        call.enqueue(object :Callback<JoinMeetingModel>{
            override fun onResponse(call: Call<JoinMeetingModel>, response: Response<JoinMeetingModel>) {
                if(response.isSuccessful){
                    var getdata=response.body()
                    var errorcode=getdata!!.status
                    var message=getdata.message
                    Log.d("successresponse",response.body().toString())// 5second waiting for loader
                    var timer = object : CountDownTimer(5000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.d("TimerStart" ,(millisUntilFinished / 1000).toString())
                            var timerr=(millisUntilFinished / 1000).toString().trim()
                            Log.d("Time",timerr)
                         }
                            override fun onFinish() {
                            if(message=="success"&&errorcode==200){
                                Constant.StopLoader()
                                IntentUrlOnBrowser(advocateName,roomId)
                            }
                        }
                    }.start()
                }
                else {
                    Log.d("Error",response.errorBody().toString())
                    Constant.StopLoader()
                    Toast.makeText(this@YourAdvocateActivity,response.errorBody().toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JoinMeetingModel>, t: Throwable) {
                Constant.StopLoader()

            }
        })
    }

    fun IntentUrlOnBrowser(userName:String,roomId:String){
        if (!isPermissionGranted()) {
            askPermissions()
        }
        else{
            var intent=Intent(this@YourAdvocateActivity, WebViewForVideoMeeting::class.java)
            intent.putExtra("room_id",roomId)
            intent.putExtra("name",userName)
            startActivity(intent)
          /*  var refNumber= (0..10).random()
            var browserIntent = Intent()
            browserIntent.setPackage("com.android.chrome")
            browserIntent.setAction(Intent.ACTION_VIEW)
            browserIntent.setData(Uri.parse("https://eadvokate.yourvideo.live/${roomId}?name=${userName.filterNot { it.isWhitespace() }}&user_ref=${refNumber}&video=yes&landing=no&audio=yes&screenshare=yes&group_chat=no&username=no&whiteboard=no&pvt_chat=no&clock=yes&fixed_toolbar=no&room_name=yes&room_lock=no"))
            startActivity(browserIntent)
            CheckIntentBrowser=true*/
        }
    }

    private fun askPermissions() {
        ActivityCompat.requestPermissions(this, permission, requestCode)
    }

    private fun isPermissionGranted(): Boolean {
        permission.forEach {
            if (ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED)
                return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var intent=Intent(this@YourAdvocateActivity, WebViewForVideoMeeting::class.java)
        intent.putExtra("room_id",roomId)
        intent.putExtra("name",advocateName)
        startActivity(intent)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            add(fragment,"feedbackfragment")
            addToBackStack("feedbackfragment")
            replace(R.id.framelayout1, fragment)
            commit()
        }


    override fun onBackPressed() {
        if(CheckIntentBrowser){

            // when video call on browser ......
            advocatelayout.visibility=View.GONE
            title.text = "Feedback"
            backarrow.visibility = View.GONE

            val feedbackfragment = FeedbackVideocall()
            bundle.putString("roomid",roomId)
            feedbackfragment.arguments=bundle
            setCurrentFragment(feedbackfragment)

            val fragment = supportFragmentManager.findFragmentByTag("feedbackfragment")

            if (fragment is FeedbackVideocall) {
                fragment.requireFragmentManager().popBackStack();
                CheckIntentBrowser=false
                finishAffinity()
            }

        }
        else{
           finish()
        }

    }


}