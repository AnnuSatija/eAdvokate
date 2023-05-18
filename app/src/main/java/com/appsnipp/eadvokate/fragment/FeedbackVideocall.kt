package com.appsnipp.eadvokate.fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.MainActivity
import com.appsnipp.eadvokate.model.FeedbackResponseModel
import com.appsnipp.eadvokate.model.RequestConsultancyDataModel
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class FeedbackVideocall : Fragment() {
    lateinit var proceedbutton:TextView
    lateinit var apiHolder: ApiHolder
    lateinit var sharedpreferences: Sharedpreferences
    lateinit var ratingVideoAudiorating:RatingBar
    lateinit var consultancyrating:RatingBar
    lateinit var suggestionText:EditText
    var Consult_rating:Int=0
    var video_rating:Int=0
    var suggestion:String=""
    var accessToken:String=""

    companion object{
        var roomid:String=""
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_feedback_videocall, container, false)
        init(view)
        setonclicklistner()
        return view
    }

    fun init(view: View){
        apiHolder=ApiClient.getApiClient(Constant.Authusername,resources.getString(R.string.authpassword)).create(ApiHolder::class.java)
        sharedpreferences= Sharedpreferences.getInstance(requireContext())
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        val bundle = arguments
        roomid  = bundle!!.getString("roomid") as String
        proceedbutton=view.findViewById(R.id.proceedbutton)
        ratingVideoAudiorating=view.findViewById(R.id.videoratingbar)
        consultancyrating=view.findViewById(R.id.consultancyrating)
        suggestionText=view.findViewById(R.id.suggestiontxt)
        proceedbutton.text="Submit"
    }

    fun setonclicklistner(){

        proceedbutton.setOnClickListener {
                Consult_rating=consultancyrating.rating.toString().toDouble().toInt()
                video_rating= ratingVideoAudiorating.rating.toString().toDouble().toInt()
                suggestion=suggestionText.text.toString().trim()

                Log.d("ConsultRating",Consult_rating.toString())
                Log.d("VideoRating",video_rating.toString())

                if(Consult_rating.toString().isNullOrBlank()||video_rating.toString().isNullOrBlank()){
                    Log.d("videoRating",video_rating.toString())

                    if(video_rating.toString().isNullOrBlank()  ){
                        Toast.makeText(requireContext(),"Please,enter video and audio rating",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(Consult_rating.toString().isNullOrBlank()){
                        Toast.makeText(requireContext(),"Please,enter consultancy rating",Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
                else{
                    HitApiForSubmitFeedback()
                }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun HitApiForSubmitFeedback(){
            Constant.loadingpopup(requireContext(),"Feedback submitting","",false)
            val requestBody: MutableMap<String, Any> = ArrayMap()
            requestBody.put("consultant_rating", Consult_rating.toString())
            requestBody.put("video_rating", video_rating.toString())
            requestBody.put("suggestion", suggestion)
            requestBody.put("room_id", roomid)
            val body: RequestBody = RequestBody.create(MediaType.parse( "application/json; charset=utf-8"), JSONObject(JSONObject(requestBody as MutableMap<*, *>).toString()).toString())
           var call=apiHolder.RequestFeedbackAfterVideoCall(Constant.HeadersWithAccess(accessToken),body)
            call.enqueue(object :Callback<FeedbackResponseModel>{
                override fun onResponse(call: Call<FeedbackResponseModel>, response: Response<FeedbackResponseModel>) {
                    Constant.StopLoader()
                    if(response.isSuccessful){
                        var errorcode=response.body()!!.status
                        if(errorcode==200){
                            suggestion=""
                            Consult_rating=0
                            video_rating=0
                            suggestionText.setText("")
                            consultancyrating.rating=0f
                            ratingVideoAudiorating.rating=0f
                            Toast.makeText(requireContext(),"Feedback uploaded successfully !!",Toast.LENGTH_SHORT).show()
                            IntentHomePage()
                        }
                    }

                }

                override fun onFailure(call: Call<FeedbackResponseModel>, t: Throwable) {
                    Constant.StopLoader()

                }

            })

    }

    fun IntentHomePage(){
        var intent=Intent(requireContext(),MainActivity::class.java)
        intent.clearStack()
        startActivity(intent)
    }

    fun Intent.clearStack(additionalFlags: Int = 0) {
        flags = additionalFlags or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }


}