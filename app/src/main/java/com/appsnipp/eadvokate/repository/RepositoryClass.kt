package com.appsnipp.eadvokate.repository

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.ArrayMap
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.SignInActivity
import com.appsnipp.eadvokate.model.*
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
import kotlin.system.exitProcess


class RepositoryClass {

    companion object {
        val consultationList=MutableLiveData<ConsultationModel>()
        val reviewDocList=MutableLiveData<ReviewDocumentModel>()
        val instantServiceList=MutableLiveData<InstantServiceCasesModel>()
        val pendingpaymentacceptList= MutableLiveData<PendingPaymentAcceptModel>()
        val allPaymentList= MutableLiveData<AllPaymentListModel>()
        val serviceDataList= MutableLiveData<ServicesDataModel>()
        val bannerDataList= MutableLiveData<DashboardBannerModel>()

        fun getConsultationApiCall(context:Context):MutableLiveData<ConsultationModel>{

            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid");
            val Call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetAllConsulationList(Constant.HeadersWithAccess(accessToken))
            Call.enqueue(object :retrofit2.Callback<ConsultationModel> {
                override fun onResponse(call: Call<ConsultationModel>, response: Response<ConsultationModel>) {
                    if(response.isSuccessful) {
                        val getbody = response.body()
                        val errorcode = getbody!!.status
                        val msg = getbody.message
                        consultationList.value = ConsultationModel(msg, getbody.response, errorcode)
                    }
                    else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val status = jObjError.getString("status")
                            if (status == "401") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT).show() }
                            else if (status == "400") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if(status=="403"){
                                alert(context)
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }

                }
                override fun onFailure(call: Call<ConsultationModel>, t: Throwable) {


                }

            })
         return consultationList
        }

        fun getReviewDocumentApiCall(context: Context):MutableLiveData<ReviewDocumentModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetReviewDocList(Constant.HeadersWithAccess(accessToken))
            call.enqueue(object : Callback<ReviewDocumentModel>{
                override fun onResponse(call: Call<ReviewDocumentModel>, response: Response<ReviewDocumentModel>) {
                    if(response.isSuccessful) {
                        val getdata = response.body()
                        val msg = response.body()?.message   // ?safe operator....
                        val status = response.body()?.status
                        reviewDocList.value = ReviewDocumentModel(msg, getdata?.response, status)
                    }   else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val status = jObjError.getString("status")
                            if (status == "401") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT).show() }
                            else if (status == "400") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if(status=="403"){
                                alert(context)
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ReviewDocumentModel>, t: Throwable) {


                }

            })
           return reviewDocList
        }

        fun getInstantServiceApiCall(context: Context):MutableLiveData<InstantServiceCasesModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetInstantServiceList(Constant.HeadersWithAccess(accessToken))
            call.enqueue(object :Callback<InstantServiceCasesModel>{

                override fun onResponse(call: Call<InstantServiceCasesModel>, response: Response<InstantServiceCasesModel>) {
                    if(response.isSuccessful) {
                        val getdata = response.body()
                        val msg = getdata?.message
                        val status = getdata?.status
                        instantServiceList.value =
                            InstantServiceCasesModel(msg, getdata!!.response, status)
                    }
                    else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val status = jObjError.getString("status")
                            if (status == "401") {
                                Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                            else if (status == "400") {
                                Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                            else if(status=="403"){
                                alert(context)
                            }
                            else{
                                Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<InstantServiceCasesModel>, t: Throwable) {

                }

            })
            return instantServiceList
        }

        fun getPendingAcceptPaymentApiCall(context: Context,paymentId:Int):MutableLiveData<PendingPaymentAcceptModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val requestBody: MutableMap<String, Any> = ArrayMap()
            requestBody.put("payment_id", paymentId)
            requestBody.put("request_from", "APP")
            requestBody.put("remark", "PROCEED_FOR_PAYMENT")
            val body: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject(requestBody.toString()).toString())
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetAcceptPendingPayment(Constant.HeadersWithAccess(accessToken),body)

            call.enqueue(object :Callback<PendingPaymentAcceptModel>{

                override fun onResponse(call: Call<PendingPaymentAcceptModel>, response: Response<PendingPaymentAcceptModel>) {
                    if(response.isSuccessful) {
                        val getdata = response.body()
                        val msg = getdata?.message
                        val status = getdata?.status
                        pendingpaymentacceptList.value =
                            PendingPaymentAcceptModel(msg, getdata!!.response, status)
                    }   else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val status = jObjError.getString("status")
                            if (status == "401") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT).show() }
                            else if (status == "400") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if(status=="403"){
                                alert(context)
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }


                override fun onFailure(call: Call<PendingPaymentAcceptModel>, t: Throwable) {

                }

            })
            return pendingpaymentacceptList
        }

        fun getAllPaymentApiCall(context: Context):MutableLiveData<AllPaymentListModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetAllPaymentList(Constant.HeadersWithAccess(accessToken))
            call.enqueue(object :Callback<AllPaymentListModel>{

                override fun onResponse(call: Call<AllPaymentListModel>, response: Response<AllPaymentListModel>) {
                        if(response.isSuccessful) {
                            val getdata = response.body()
                            val msg = getdata?.message
                            val status = getdata?.status
                            allPaymentList.value =
                                AllPaymentListModel(msg, getdata?.response, status)
                        }
                        else{
                            try {
                                    val jObjError = JSONObject(response.errorBody()!!.string())
                                    val status = jObjError.getString("status")
                                    if (status == "401") {
                                        Toast.makeText(
                                            context,
                                            jObjError.getString("message"),
                                            Toast.LENGTH_SHORT).show() }
                                    else if (status == "400") {
                                        Toast.makeText(
                                            context,
                                            jObjError.getString("message"),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else if(status=="403"){
                                        alert(context)
                                    }
                                    else{
                                        Toast.makeText(
                                            context,
                                            jObjError.getString("message"),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                }

                override fun onFailure(call: Call<AllPaymentListModel>, t: Throwable) {

                }

            })
            return allPaymentList
        }

        fun getServicesApiCall(context: Context):MutableLiveData<ServicesDataModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetServices(Constant.HeadersWithAccess(accessToken))
            call.enqueue(object :Callback<ServicesDataModel>{

                override fun onResponse(
                    call: Call<ServicesDataModel>, response: Response<ServicesDataModel>) {
                    if(response.isSuccessful) {
                        val getdata = response.body()
                        val msg = getdata?.message
                        val status = getdata?.status
                        serviceDataList.value = ServicesDataModel(msg, getdata?.response, status)
                    }
                    else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val status = jObjError.getString("status")
                            if (status == "401") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT).show() }
                            else if (status == "400") {
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else if(status=="403"){
                                alert(context)
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    jObjError.getString("message"),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ServicesDataModel>, t: Throwable) {


                }

            })
          return  serviceDataList
        }

        fun getBannerApiCall(context: Context):MutableLiveData<DashboardBannerModel>{
            val accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
            val call= ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).GetBannerList(Constant.HeadersWithAccess(accessToken))
            call.enqueue(object : Callback<DashboardBannerModel>{

                override fun onResponse(
                    call: Call<DashboardBannerModel>,
                    response: Response<DashboardBannerModel>) {
                    val getdata=response.body()
                    val msg=getdata?.message
                    val status=getdata?.status
                    bannerDataList.value= DashboardBannerModel(msg,getdata?.response,status)

                }


                override fun onFailure(call: Call<DashboardBannerModel>, t: Throwable) {

                }

            })
            return bannerDataList
        }

      fun alert(context: Context){
          var dialog: Dialog? =null
          dialog = Dialog(context!!, R.style.Theme_Transparent)
          dialog!!.setContentView(R.layout.logoutalert)
          dialog!!.setCanceledOnTouchOutside(false)
          dialog!!.setCancelable(false)

          var cancel=dialog.findViewById<TextView>(R.id.cancel)
          var logout=dialog.findViewById<TextView>(R.id.logout)
          var text=dialog.findViewById<TextView>(R.id.title)

          cancel.visibility= View.GONE
          logout.text="Login again"
          text.text="You'r access token expired now!!"

          logout.setOnClickListener {
              if(Sharedpreferences.getInstance(context).getlogged("Userlogin")){
                  Sharedpreferences.getInstance(context).islogged("Userlogin",false)
                  var intent= Intent(context, SignInActivity::class.java)
                  intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                  context.startActivity(intent)
              }
          }

          dialog.show()
      }

    }



}