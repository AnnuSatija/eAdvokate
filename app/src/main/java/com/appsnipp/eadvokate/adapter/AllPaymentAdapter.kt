package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.databinding.PendingPaymentItemlayoutBinding
import com.appsnipp.eadvokate.model.AllPaymentResponse
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass

class AllPaymentAdapter(val context: Context, val allpaymentList: MutableList<AllPaymentResponse>?) : RecyclerView.Adapter<AllPaymentAdapter.ModelViewHolder>() {
   lateinit var apiHolder:ApiHolder
   lateinit var accessToken:String
   lateinit var sharedpreferences: Sharedpreferences
   lateinit var viewModel:ViewModelClass

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        return ModelViewHolder(PendingPaymentItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return allpaymentList!!.size
    }

    override fun onBindViewHolder(holder: AllPaymentAdapter.ModelViewHolder, position: Int) {
      holder.binding.allPaymentCard.visibility=View.VISIBLE
      holder.binding.pendingPaymentCard.visibility=View.GONE

      holder.binding.allPaymentservicename.text="Service - "+allpaymentList!![position].service_name
      holder.binding.allPaymentamount.text="₹"+allpaymentList!![position].amount
      holder.binding.allPaymenttxndate.text="Txn Date - "+allpaymentList!![position].order_date
      holder.binding.allPaymentorderId.text=allpaymentList!![position].order_id

      if(allpaymentList[position].order_status.equals("Success")) {
          holder.binding.allPaymentstatus.text = Html.fromHtml("Status- " + "<font color=\"#2B9962\">" +allpaymentList!![position].order_status + "</font>")
      }else{
          holder.binding.allPaymentstatus.text = Html.fromHtml("Status- " + "<font color=\"#ED1C24\">" +"Failed" + "</font>")
      }

    }

    class ModelViewHolder(val binding:PendingPaymentItemlayoutBinding) : RecyclerView.ViewHolder(binding.root)


}