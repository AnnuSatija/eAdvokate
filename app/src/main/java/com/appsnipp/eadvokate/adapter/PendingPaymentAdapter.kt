package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.content.Intent
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.view.activity.PaymentRefundActivity
import com.appsnipp.eadvokate.databinding.PendingPaymentItemlayoutBinding
import com.appsnipp.eadvokate.model.PaymentCancelModel
import com.appsnipp.eadvokate.model.RequestResponse
import com.appsnipp.eadvokate.multimedia.WebActivityForCCAvenuePayment
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendingPaymentAdapter(val context: Context, val pendingList: MutableList<RequestResponse>) : RecyclerView.Adapter<PendingPaymentAdapter.ModelViewHolder>() {
   lateinit var apiHolder:ApiHolder
   lateinit var accessToken:String
   lateinit var sharedpreferences: Sharedpreferences
   lateinit var viewModel:ViewModelClass

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder(PendingPaymentItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return pendingList.size
    }

    override fun onBindViewHolder(holder: PendingPaymentAdapter.ModelViewHolder, position: Int) {
      viewModel=ViewModelProvider(context as PaymentRefundActivity)[ViewModelClass::class.java]
      holder.binding.allPaymentCard.visibility=View.GONE
      holder.binding.pendingPaymentCard.visibility=View.VISIBLE

      holder.binding.serviceNametxt.text=pendingList.get(position).service_id
      holder.binding.amounttxt.text="₹"+pendingList.get(position).amount
      holder.binding.requestdate.text=pendingList.get(position).generated_at

      holder.binding.cancelcard.setOnClickListener {
          HitApiforCancelPendingPaymentList(pendingList[position].id.toString())
      }

     holder.binding.acceptcard.setOnClickListener {
         Constant.loadingpopup(context,"Requesting","",false)
         viewModel.getPendingPaymentAcceptList(context,pendingList[position].id).observe(context, Observer { pendingAccept->
           val mag=pendingAccept.message
           val errorcode=pendingAccept.status
           val getdata=pendingAccept.response
           if(errorcode==200){
               Constant.StopLoader()
               if(getdata.order_hash.isNotBlank()){
                   val intent= Intent(context, WebActivityForCCAvenuePayment::class.java)
                   intent.putExtra("order_hash",getdata.order_hash)
                   context.startActivity(intent)
               }
           }

           else{

           }

       })

     }

    }

    class ModelViewHolder(val binding:PendingPaymentItemlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    fun HitApiforCancelPendingPaymentList(paymentId:String){
        Constant.loadingpopup(context,"Cancelling","",false)
        accessToken=Sharedpreferences.getInstance(context).getAccessToken("connect_sid")
        val requestBody: MutableMap<String, Any> = ArrayMap()
        requestBody.put("payment_id", paymentId)
        requestBody.put("remark", "USER_CANCELLED_WITHOUT_PG")

        val body: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject(requestBody.toString()).toString())

        var call=ApiClient.getApiClient(Constant.Authusername,context.resources.getString(R.string.authpassword)).create(ApiHolder::class.java).CancelPayment(Constant.HeadersWithAccess(accessToken),body)
        call.enqueue(object :Callback<PaymentCancelModel>{

            override fun onResponse(call: Call<PaymentCancelModel>, response: Response<PaymentCancelModel>) {
                Constant.StopLoader()
                if(response.isSuccessful){
                    (context as PaymentRefundActivity).HitApiForPaymentPending()
                }
            }

            override fun onFailure(call: Call<PaymentCancelModel>, t: Throwable) {
                Constant.StopLoader()
            }

        })


    }


}