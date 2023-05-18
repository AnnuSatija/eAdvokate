package com.appsnipp.eadvokate.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.databinding.ReviewDocItemlayoutBinding
import com.appsnipp.eadvokate.model.InstantServiceCasesModel
import com.appsnipp.eadvokate.model.InstantServiceResponse
import com.appsnipp.eadvokate.model.ReviewDocResponse
import com.appsnipp.eadvokate.multimedia.OpenMedia

class InstantServiceCasesAdapter(val context: Context, val reviewDocList:MutableList<InstantServiceResponse>?):RecyclerView.Adapter<InstantServiceCasesAdapter.ViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReviewDocItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
     }


     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.binding.instanceServiceCard.visibility= View.VISIBLE
         holder.binding.reviewdocCard.visibility= View.GONE
         holder.binding.instanceservicename.text=reviewDocList!![position].service_name
         holder.binding.instanceserviceorderId.text="Case ID - "+ reviewDocList!![position].order_id
         holder.binding.instanceserviceamount.text="Amount- ₹"+reviewDocList[position].trans_amount
         if(reviewDocList[position].status.equals("Closed")) {
             holder.binding.instanceservicestatus.text =
                 Html.fromHtml("Status- " + "<font color=\"#29A62E\">" + reviewDocList[position].status + "</font>")
         }
         else{
             holder.binding.instanceservicestatus.text =
                 Html.fromHtml("Status- " + "<font color=\"#E3892A\">" + reviewDocList[position].status + "</font>")
         }
         holder.binding.instanceservicestartdate.text="Case Start Date - "+reviewDocList[position].case_start_date
         holder.binding.instanceserviceclosedate.text="Case Close Date - "+reviewDocList[position].case_complete_date


     }

     override fun getItemCount(): Int {
         return reviewDocList!!.size
     }

     class ViewHolder(val binding:ReviewDocItemlayoutBinding):RecyclerView.ViewHolder(binding.root)

 }