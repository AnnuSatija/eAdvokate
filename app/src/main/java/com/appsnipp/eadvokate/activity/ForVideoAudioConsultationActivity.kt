package com.appsnipp.eadvokate.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.ArrayMap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.RequestConsultancyDataModel
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.here.oksse.OkSse
import com.here.oksse.ServerSentEvent
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit


class ForVideoAudioConsultationActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit var backarrow: CardView
    lateinit var apiHolder: ApiHolder
    var accessToken: String = ""
    lateinit var timerLoader: Dialog
    lateinit var englistcharimage: ImageView
    lateinit var hindicharimage: ImageView
    lateinit var englisttxt: TextView
    lateinit var hinditxt: TextView
    lateinit var hindicard: LinearLayout
    lateinit var englishcard: LinearLayout
    lateinit var videoimage: ImageView
    lateinit var audioimage: ImageView
    lateinit var videotxt: TextView
    lateinit var audiotxt: TextView
    lateinit var continuewbutton: TextView
    lateinit var videocard: LinearLayout
    lateinit var audiocard: LinearLayout
    var serviceType: Int = 0
    var serviceId: Int = 0
    var consultLanguage: String = ""
    var consultMode: String = ""
    var type: String = ""
    lateinit var serversentevent: ServerSentEvent

    private val mHandler: Handler = Handler()

    companion object {
        lateinit var timer: CountDownTimer
    }

    var Count = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        loadlocale()
        setContentView(R.layout.activity_video_audio_cunsultancy)
        getIntentValue()
        init()
        setclicklistner()
    }

    fun init() {
        apiHolder = ApiClient.getApiClient(
            Constant.Authusername,
            resources.getString(R.string.authpassword)
        ).create(ApiHolder::class.java)
        sharedpreferences = Sharedpreferences.getInstance(this)
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        backarrow = findViewById(R.id.backarrow)

        englistcharimage = findViewById<ImageView>(R.id.englishcharimage)
        hindicharimage = findViewById<ImageView>(R.id.hindicharimage)
        englisttxt = findViewById<TextView>(R.id.englisttxt)
        hinditxt = findViewById<TextView>(R.id.hinditxt)
        hindicard = findViewById<LinearLayout>(R.id.hindicard)
        englishcard = findViewById<LinearLayout>(R.id.englishcard)

        videoimage = findViewById<ImageView>(R.id.videoimage)
        audioimage = findViewById<ImageView>(R.id.audioimage)
        videotxt = findViewById<TextView>(R.id.videotxt)
        audiotxt = findViewById<TextView>(R.id.audiotxt)
        videocard = findViewById<LinearLayout>(R.id.videocard)
        audiocard = findViewById<LinearLayout>(R.id.audiocard)
        continuewbutton = findViewById<TextView>(R.id.contbutton)

        if(!type.isNullOrBlank()){
            if(type.equals("Video")){
                consultMode = "Video"
                videocard.setBackgroundResource(R.drawable.selectlanguageborder)
                videotxt.setTextColor(resources.getColor(R.color.red))
                ImageViewCompat.setImageTintList(
                    videoimage,
                    ColorStateList.valueOf(resources.getColor(R.color.red))
                )
                audiocard.setBackgroundResource(R.drawable.edittextback)
                audiotxt.setTextColor(resources.getColor(R.color.black))
                ImageViewCompat.setImageTintList(
                    audioimage,
                    ColorStateList.valueOf(resources.getColor(R.color.black)))

                consultLanguage = "English"
                englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
                englisttxt.setTextColor(resources.getColor(R.color.red))
                ImageViewCompat.setImageTintList(
                    englistcharimage,
                    ColorStateList.valueOf(resources.getColor(R.color.red))
                )
                hindicard.setBackgroundResource(R.drawable.edittextback)
                hinditxt.setTextColor(resources.getColor(R.color.black))
                ImageViewCompat.setImageTintList(
                    hindicharimage,
                    ColorStateList.valueOf(resources.getColor(R.color.black))
                )
            }
            else{
                consultMode = "Audio"

                audiocard.setBackgroundResource(R.drawable.selectlanguageborder)
                audiotxt.setTextColor(resources.getColor(R.color.red))
                ImageViewCompat.setImageTintList(audioimage, ColorStateList.valueOf(resources.getColor(R.color.red)))

                videocard.setBackgroundResource(R.drawable.edittextback)
                videotxt.setTextColor(resources.getColor(R.color.black))
                ImageViewCompat.setImageTintList(videoimage, ColorStateList.valueOf(resources.getColor(R.color.black)))

                consultLanguage = "English"
                englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
                englisttxt.setTextColor(resources.getColor(R.color.red))
                ImageViewCompat.setImageTintList(
                    englistcharimage,
                    ColorStateList.valueOf(resources.getColor(R.color.red))
                )
                hindicard.setBackgroundResource(R.drawable.edittextback)
                hinditxt.setTextColor(resources.getColor(R.color.black))
                ImageViewCompat.setImageTintList(
                    hindicharimage,
                    ColorStateList.valueOf(resources.getColor(R.color.black))
                )
            }
        }

    }

    fun getIntentValue() {
        if (intent.hasExtra("service_type")) {
            serviceType = intent.getIntExtra("service_type", 0)
        }
        if (intent.hasExtra("service_id")) {
            serviceId = intent.getIntExtra("service_id", 0)
        }
        if(intent.hasExtra("callingtype")){
            type=intent.getStringExtra("callingtype") as String
        }
    }

    fun setclicklistner() {
        backarrow.setOnClickListener {
            finish()
        }

        hindicard.setOnClickListener {
            consultLanguage = "Hindi"
            hindicard.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setTextColor(resources.getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(resources.getColor(R.color.red))
            )
            englishcard.setBackgroundResource(R.drawable.edittextback)
            englisttxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(resources.getColor(R.color.black))
            )
        }

        englishcard.setOnClickListener {
            consultLanguage = "English"
            englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
            englisttxt.setTextColor(resources.getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                englistcharimage,
                ColorStateList.valueOf(resources.getColor(R.color.red))
            )
            hindicard.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                hindicharimage,
                ColorStateList.valueOf(resources.getColor(R.color.black))
            )
        }

        videocard.setOnClickListener {
            consultMode = "Video"
            videocard.setBackgroundResource(R.drawable.selectlanguageborder)
            videotxt.setTextColor(resources.getColor(R.color.red))
            ImageViewCompat.setImageTintList(
                videoimage,
                ColorStateList.valueOf(resources.getColor(R.color.red))
            )
            audiocard.setBackgroundResource(R.drawable.edittextback)
            audiotxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(
                audioimage,
                ColorStateList.valueOf(resources.getColor(R.color.black))
            )
        }

        audiocard.setOnClickListener {
            consultMode = "Audio"

            audiocard.setBackgroundResource(R.drawable.selectlanguageborder)
            audiotxt.setTextColor(resources.getColor(R.color.red))
            ImageViewCompat.setImageTintList(audioimage, ColorStateList.valueOf(resources.getColor(R.color.red)))

            videocard.setBackgroundResource(R.drawable.edittextback)
            videotxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(videoimage, ColorStateList.valueOf(resources.getColor(R.color.black)))

        }

        continuewbutton.setOnClickListener {
            if (consultLanguage.isNullOrBlank() || consultMode.isNullOrBlank()) {
                if (consultLanguage.isNullOrBlank()) {
                    Toast.makeText(this, "Please select language first!!", Toast.LENGTH_SHORT)
                        .show()
                } else if (consultMode.isNullOrBlank()) {
                    Toast.makeText(this, "Please select consultancy mode!!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                if (serviceType > 0 && serviceId > 0) {
                    HitApiForVideoAudioCallConsulation(serviceId, serviceType)
                }
            }

        }
    }

    fun resetMethod(){
        if(consultMode.isNullOrBlank()||consultLanguage.isNullOrBlank()){
            videocard.setBackgroundResource(R.drawable.edittextback)
            videotxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(videoimage, ColorStateList.valueOf(resources.getColor(R.color.black)))
            audiocard.setBackgroundResource(R.drawable.edittextback)
            audiotxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(audioimage, ColorStateList.valueOf(resources.getColor(R.color.black)))
            englishcard.setBackgroundResource(R.drawable.edittextback)
            englisttxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(englistcharimage, ColorStateList.valueOf(resources.getColor(R.color.black)))
            hindicard.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(resources.getColor(R.color.black))
            ImageViewCompat.setImageTintList(hindicharimage, ColorStateList.valueOf(resources.getColor(R.color.black)))
        }
    }


    fun HitApiForVideoAudioCallConsulation(serviceId: Int, serviceType: Int) {
        timerLoader = Constant.loadingpopup(
            this@ForVideoAudioConsultationActivity,
            "Please Wait, We are looking for a legal expert to assist you further",
            "",
            false
        )
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("service_type", serviceType)
        requestBody.put("service_id", serviceId)
        requestBody.put("consult_language", consultLanguage)
        requestBody.put("consultancy_mode", consultMode)

        val body: RequestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"), JSONObject(
                JSONObject(requestBody as MutableMap<*, *>).toString()
            ).toString()
        )

        var call = apiHolder.RequestConsultancy(Constant.HeadersWithAccess(accessToken), body)

        call.enqueue(object : Callback<RequestConsultancyDataModel> {
            override fun onResponse(
                call: Call<RequestConsultancyDataModel>?,
                response: Response<RequestConsultancyDataModel>?
            ) {
                if (response!!.isSuccessful) {
                    consultLanguage = ""
                    var getdata = response.body()
                    var errorcode = getdata!!.status
                    if (errorcode == 200) {
                        var requestId = getdata.response.request_id
                        var customerId = getdata.response.customer_id
                        var customerName = getdata.response.customer_name
                        var requestTime = getdata.response.request_time
                        var consultancyMode = getdata.response.consultancy_mode
                        var language = getdata.response.language
                        var service = getdata.response.service

                        val client = OkHttpClient.Builder()
                            .readTimeout(15, TimeUnit.SECONDS)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build()

                        val request = Request.Builder()
                            .url(Constant.BaseUrl + "events/intant-request-approval/advocate-info")
                            .header("Accept", "application/json; q=0.5")
                            .addHeader("Accept", "text/event-stream")
                            .addHeader("x-access-token", accessToken)
                            .build()

                        val okSse = OkSse(client)
                        starTimer()
                        serversentevent =
                            okSse.newServerSentEvent(request, object : ServerSentEvent.Listener {

                                override fun onOpen(
                                    sse: ServerSentEvent?,
                                    response: okhttp3.Response?
                                ) {
                                    Log.d("Open", response.toString())
                                }

                                override fun onMessage(
                                    sse: ServerSentEvent,
                                    id: String,
                                    event: String,
                                    message: String
                                ) {
                                    Log.d(
                                        "Event",
                                        event + "Message->" + message
                                    )//request-consultancy-approval-209-cl_0018

                                    if (event == "request-consultancy-approval-$requestId-$customerId") {
                                        var jsonObject = JSONObject(message)
                                        var name = jsonObject.getString("name")
                                        var experiencr = jsonObject.getString("experience")
                                        var description = jsonObject.getString("description")
                                        var photo = jsonObject.getString("photo")
                                        if (consultancyMode == "Video") {
                                            RequestForRoomCreate(
                                                requestId,
                                                customerId,
                                                name,
                                                description,
                                                experiencr,
                                                photo,
                                                consultMode
                                            )
                                        } else {
                                            Constant.StopLoader()
                                            serversentevent.close()
                                            timer.cancel()
                                            resetMethod()
                                            var intent = Intent(this@ForVideoAudioConsultationActivity, YourAdvocateActivity::class.java)
                                            intent.putExtra("name", name)
                                            intent.putExtra("description", description)
                                            intent.putExtra("experience", experiencr)
                                            intent.putExtra("photo", photo)
                                            intent.putExtra("mode", consultMode)
                                            intent.putExtra("paymentdetailsfirst", "payment")
                                            startActivity(intent)

                                        }
                                    }

                                }

                                override fun onComment(sse: ServerSentEvent?, comment: String?) {

                                    Log.d("Coment", comment.toString())
                                }

                                override fun onRetryTime(
                                    sse: ServerSentEvent?,
                                    milliseconds: Long
                                ): Boolean {
                                    Log.d("onretrytime", milliseconds.toString())
                                    return true
                                }

                                override fun onRetryError(
                                    sse: ServerSentEvent?,
                                    throwable: Throwable?,
                                    response: okhttp3.Response?
                                ): Boolean {
                                    Log.d("retryerror", response.toString())
                                    //Response{protocol=http/1.1, code=403, message=Forbidden, url=https://api.eadvokate.com/events/intant-request-approval/advocate-info}
                                    return true
                                }

                                override fun onClosed(sse: ServerSentEvent?) {
                                    Log.d("onClosde", sse.toString())
                                }

                                override fun onPreRetry(
                                    sse: ServerSentEvent,
                                    originalRequest: Request
                                ): Request {
                                    return request
                                }

                            })


                    }

                }

            }

            override fun onFailure(call: Call<RequestConsultancyDataModel>?, t: Throwable?) {
                TODO("Not yet implemented")
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
        applicationContext.resources.updateConfiguration(
            configuration,
            applicationContext.resources.displayMetrics
        )
        sharedpreferences!!.setlanguage("MyLanguage", lang)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    fun starTimer() {
        Count = 10
        var timerr: String = ""
        this.runOnUiThread(object : Runnable {
            override fun run() {
                timer = object : CountDownTimer(60000, 1000) {//60 second = 1 minutes
                    override fun onTick(millisUntilFinished: Long) {

                        Log.d("TimerStart", (millisUntilFinished / 1000).toString())
                        timerr = (millisUntilFinished / 1000).toString().trim()
                        Log.d("overtime", timerr)
                        if (timerr == "0") {
                           // Constant.messSet = true
                            Constant.loadingText?.text =
                                "It looks like our expert is busy on another call, Be patient. Your call will be answered shortly."
                            /*Constant.loadingpopup(
                                this@ForVideoAudioConsultationActivity,
                                ,
                                "",
                                false
                            )*/
                            timer.cancel()
                            startTimeForNext30Second()
                        }
                    }

                    override fun onFinish() {
                    }
                }.start()
            }

        })

    }

    fun startTimeForNext30Second(): String {
        var timerr: String = ""
        this.runOnUiThread(object : Runnable {
            override fun run() {
                timer = object : CountDownTimer(50000, 1000) { //50 second = 1 minutes
                    override fun onTick(millisUntilFinished: Long) {
                        Log.d("TimerStart", (millisUntilFinished / 1000).toString())
                        timerr = (millisUntilFinished / 1000).toString()
                        Log.d("50 second", timerr)
                        if (timerr == "0") {//10
                            timer.cancel()
                            callForFinalAlert()
                        }
                    }

                    override fun onFinish() {
                        serversentevent.close()
                    }

                }.start()
            }

        })
        return timerr
    }

    fun callForFinalAlert() {
        /*var loadingPopup = Constant.loadingpopup(
            this@ForVideoAudioConsultationActivity,
            "Sorry, it looks like our advocates are busy handling another client.\nYou can try again, or our executive will call you\n",
            Count.toString(),
            true, false
        )
        Constant.showDialog(loadingPopup)*/
       // Constant.messSet = true
       // Constant.mess =
        var timerr: String = ""
        Constant.loadingText?.text="Sorry, it looks like our advocates are busy handling another client.\nYou can try again, or our executive will call you\n"
        Constant.timecount?.text = Count.toString()
        Constant.timecount?.visibility = View.VISIBLE
        Constant.countmsg?.visibility = View.VISIBLE

        this.runOnUiThread(object : Runnable {
            override fun run() {
                timer = object : CountDownTimer(10000, 1000) {// 10 second
                    override fun onTick(millisUntilFinished: Long) {
                        timerr = (millisUntilFinished / 1000).toString()
                        Log.d("EndTime", timerr)
                        if (Count >= 0) {
                            Constant.timecount?.text = timerr
                        }
                        Count--
                    }

                    override fun onFinish() {
                        timer.cancel()
                        Constant.StopLoader()
                        resetMethod()

                    }

                }

                timer.start()

            }
        })
    }


    fun RequestForRoomCreate(
        requestId: Int,
        customerId: String,
        Name: String,
        Description: String,
        Experience: String,
        Photo: String,
        consultantmode: String
    ) {
        val client = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(Constant.BaseUrl + "events/meeting-room/meeting-room-created")
            .header("Accept", "application/json; q=0.5")
            .addHeader("Accept", "text/event-stream")
            .addHeader("x-access-token", accessToken)
            .build()

        val okSse = OkSse(client)

        serversentevent = okSse.newServerSentEvent(request, object : ServerSentEvent.Listener {
            override fun onOpen(sse: ServerSentEvent?, response: okhttp3.Response?) {
                Log.d("Open", response.toString())
            }

            override fun onMessage(
                sse: ServerSentEvent,
                id: String,
                event: String,
                message: String
            ) {

                Log.d("CreatedRoomEvent", event + "RoomCreatedMessage->" + message)

                if (event == "meeting-room-created-$requestId-$customerId") {
                    Constant.StopLoader()
                    serversentevent.close()
                    resetMethod()

                    var jsonObject = JSONObject(message)
                    var roomId = jsonObject.getString("room_id")
                    var meetingId = jsonObject.getString("meeting_id")
                    timer.cancel()
                    var intent = Intent(
                        this@ForVideoAudioConsultationActivity, YourAdvocateActivity::class.java)
                    intent.putExtra("name", Name)
                    intent.putExtra("description", Description)
                    intent.putExtra("experience", Experience)
                    intent.putExtra("photo", Photo)
                    intent.putExtra("mode", consultantmode)
                    intent.putExtra("room_id", roomId)
                    intent.putExtra("meeting_id", meetingId)
                    intent.putExtra("paymentdetailsfirst", "payment")
                    sharedpreferences!!.setData("meetingId", meetingId)
                    startActivity(intent)

                }
            }

            override fun onComment(sse: ServerSentEvent?, comment: String?) {
                Log.d("Coment", comment.toString())
            }

            override fun onRetryTime(sse: ServerSentEvent?, milliseconds: Long): Boolean {
                Log.d("onretrytime", milliseconds.toString())
                return true
            }

            override fun onRetryError(
                sse: ServerSentEvent?,
                throwable: Throwable?,
                response: okhttp3.Response?
            ): Boolean {
                Log.d("retryerror", response.toString())
                return true
            }

            override fun onClosed(sse: ServerSentEvent?) {
                Log.d("onClosde", sse.toString())
            }

            override fun onPreRetry(sse: ServerSentEvent, originalRequest: Request): Request {
                return request
            }

        })
    }

    fun initialPosition() {
        englishcard.setBackgroundResource(R.drawable.edittextback)
        englisttxt.setTextColor(resources.getColor(R.color.black))
        ImageViewCompat.setImageTintList(
            englistcharimage,
            ColorStateList.valueOf(resources.getColor(R.color.black))
        )
        hindicard.setBackgroundResource(R.drawable.edittextback)
        hinditxt.setTextColor(resources.getColor(R.color.black))
        ImageViewCompat.setImageTintList(
            hindicharimage,
            ColorStateList.valueOf(resources.getColor(R.color.black))
        )
        audiocard.setBackgroundResource(R.drawable.edittextback)
        audiotxt.setTextColor(resources.getColor(R.color.black))
        ImageViewCompat.setImageTintList(
            audioimage,
            ColorStateList.valueOf(resources.getColor(R.color.black))
        )
        videocard.setBackgroundResource(R.drawable.edittextback)
        videotxt.setTextColor(resources.getColor(R.color.black))
        ImageViewCompat.setImageTintList(
            videoimage,
            ColorStateList.valueOf(resources.getColor(R.color.black))
        )
    }

    override fun onResume() {
        super.onResume()
        if (consultLanguage.isNullOrBlank() || consultMode.isNullOrBlank()) {
            initialPosition()
        }
    }


}

