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
import com.appsnipp.eadvokate.model.ReviewDocResponse
import com.appsnipp.eadvokate.multimedia.OpenMedia

class ReviewDocAdapter(val context: Context, val reviewDocList:MutableList<ReviewDocResponse>?):RecyclerView.Adapter<ReviewDocAdapter.ViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReviewDocItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
     }


     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.binding.instanceServiceCard.visibility= View.GONE
         holder.binding.reviewdocCard.visibility= View.VISIBLE
         holder.binding.orderId.text= reviewDocList!![position].order_id
         holder.binding.amount.text="Amount- ₹"+reviewDocList[position].trans_amount

         if(reviewDocList[position].status.equals("Closed")) {
             holder.binding.status.text =
                 Html.fromHtml("Status- " + "<font color=\"#29A62E\">" + reviewDocList[position].status + "</font>")
         }
         else{
             holder.binding.status.text =
                 Html.fromHtml("Status- " + "<font color=\"#E3892A\">" + reviewDocList[position].status + "</font>")
         }
         holder.binding.startdate.text="Case Start Date - "+reviewDocList[position].case_start_date
         holder.binding.closedate.text="Case Close Date - "+reviewDocList[position].case_complete_date
         holder.binding.remark.text="Remark - "+reviewDocList[position].remark
         if(reviewDocList!![position].case_complete_doc.equals("")){
             holder.binding.reviewdoclayout.visibility=View.GONE
         }else{
             holder.binding.reviewdoclayout.visibility=View.VISIBLE
             holder.binding.reviewdoclayout.setOnClickListener {
                 var intent=Intent(context,OpenMedia::class.java)
                 intent.putExtra("title","Finalized Document")
                 intent.putExtra("url",reviewDocList!![position].case_complete_doc)
                 context.startActivity(intent)
             }
         }

         holder.binding.yourDocument.setOnClickListener {
             var intent=Intent(context,OpenMedia::class.java)
             intent.putExtra("title","Your Document")
             intent.putExtra("url",reviewDocList!![position].case_document)
             context.startActivity(intent)
         }

     }

     override fun getItemCount(): Int {
         return reviewDocList!!.size
     }

     class ViewHolder(val binding:ReviewDocItemlayoutBinding):RecyclerView.ViewHolder(binding.root)

 }