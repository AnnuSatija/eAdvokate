package com.appsnipp.eadvokate.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.DocumentReviewActivity
import com.appsnipp.eadvokate.activity.ForVideoAudioConsultationActivity
import com.appsnipp.eadvokate.activity.KnowYourRightsDetailsPageActivity
import com.appsnipp.eadvokate.activity.YourAdvocateActivity
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.ServicesResponse
import com.appsnipp.eadvokate.utils.Constant
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


class InstanceServiceAdapter(val context: Context, val categoriesList: List<InstanceServiceModel>, val servicelist:MutableList<ServicesResponse>, val type:String):RecyclerView.Adapter<InstanceServiceAdapter.ViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstanceServiceAdapter.ViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.instanceserviceitemlayout, parent, false)
         return ViewHolder(view)
     }

     override fun onBindViewHolder(holder: InstanceServiceAdapter.ViewHolder, position: Int) {
         if(type.equals("instance")){
             holder.instancelayout.visibility=View.VISIBLE
             holder.whatsnewlayout.visibility=View.GONE
             holder.trendinglayout.visibility=View.GONE
             holder.knowrights.visibility=View.GONE
             holder.feedsitemcard.visibility=View.GONE
             Glide.with(context).load(servicelist[position].image).into( holder.imageView)
             holder.textView.text= servicelist[position].service_name

             holder.itemView.setOnClickListener {

                  if(servicelist[position].service_name == context.getString(R.string.revdocmsg))
                 {
                    context.startActivity(Intent(context,DocumentReviewActivity::class.java))
                 }
                 else{
                      var serviceType=servicelist[position].service_type
                      var serviceId=servicelist[position].id

                        var intent=Intent(context,ForVideoAudioConsultationActivity::class.java)
                        intent.putExtra("service_type", serviceType)
                        intent.putExtra("service_id", serviceId)
                        context.startActivity(intent)
                  }

             }
         }
         else if(type.equals("trendingservices")){
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.GONE
             holder.knowrights.visibility=View.GONE
             holder.feedsitemcard.visibility=View.GONE
             holder.trendinglayout.visibility=View.VISIBLE
             holder.imagetrending.setImageResource(categoriesList.get(position).image)
             holder.servicestxt.text=categoriesList.get(position).name
         }
         else if(type.equals("knowrightsservices")){
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.GONE
             holder.trendinglayout.visibility=View.GONE
             holder.feedsitemcard.visibility=View.GONE
             holder.knowrights.visibility=View.VISIBLE
             holder.rightknowimage.setImageResource(categoriesList.get(position).image)
             holder.rightknowxt.text=categoriesList.get(position).name
             holder.itemView.setOnClickListener {
                 var intent=Intent(context,KnowYourRightsDetailsPageActivity::class.java)
                 intent.putExtra("image",categoriesList[position].image)
                 intent.putExtra("detail",categoriesList[position].details)
                 intent.putExtra("title",categoriesList[position].name)
                 context.startActivity(intent)
             }
         }
         else if(type.equals("feedslist")){
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.GONE
             holder.trendinglayout.visibility=View.GONE
             holder.knowrights.visibility=View.GONE
             holder.feedsitemcard.visibility=View.VISIBLE
             holder.feedimage.setImageResource(categoriesList.get(position).image)
             holder.feedmsg.setText(categoriesList.get(position).name)
         }
         else{
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.VISIBLE
             holder.trendinglayout.visibility=View.GONE
             val shape = GradientDrawable()
             shape.cornerRadius = 15f
             if(categoriesList.get(position).name.equals(context.getString(R.string.civilmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.civilcolor))
                // holder.whatsnewlayout.setBackgroundColor(ContextCompat.getColor(context, R.color.civilcolor))
             }else  if(categoriesList.get(position).name.equals(context.getString(R.string.propertymsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.propertycolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.corpormsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.corporatecolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.criminalmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.criminalcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.divorcemsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.divorcecolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.taxmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.taxationcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.chequbouncmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.civilcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.envmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.enviornmentcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.animlmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.animalcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.dommsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.domesticcolor))
             }
             else  if(categoriesList.get(position).name.equals(context.getString(R.string.sexmsg))){
                 shape.setColor(ContextCompat.getColor(context, R.color.sexualcolor))
             }
             else{
                 shape.setColor(ContextCompat.getColor(context, R.color.motorcolor))
             }
             holder.whatsnewlayout.background=shape
             holder.imageView1.setImageResource(categoriesList.get(position).image)
             holder.textView1.text=categoriesList.get(position).name
         }

     }

     override fun getItemCount(): Int {
          if(categoriesList.size>0)
              return categoriesList.size
         else
             return servicelist.size
     }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val imageView: ImageView = itemView.findViewById(R.id.catimage)
         val textView: TextView = itemView.findViewById(R.id.catname)
         val whatsnewlayout: CardView = itemView.findViewById(R.id.whatsnewlayout)
         val knowrights: CardView = itemView.findViewById(R.id.knowrights)
         val feedsitemcard: CardView = itemView.findViewById(R.id.feedsitemcard)
         val instancelayout: CardView = itemView.findViewById(R.id.instancelayout)
         val imageView1: ImageView = itemView.findViewById(R.id.whatsimage)
         val textView1: TextView = itemView.findViewById(R.id.whatsname)
         val trendinglayout: RelativeLayout = itemView.findViewById(R.id.trendinglayout)
         val imagetrending: ImageView = itemView.findViewById(R.id.image)
         val servicestxt: TextView = itemView.findViewById(R.id.servicestxt)
         val rightknowxt: TextView = itemView.findViewById(R.id.rightknow)
         val rightknowimage: ImageView = itemView.findViewById(R.id.rightknowimage)
         val feedimage: ImageView = itemView.findViewById(R.id.feedimage)
         val savefeed: ImageView = itemView.findViewById(R.id.savefeed)
         val feedmsg: TextView = itemView.findViewById(R.id.feedmsg)

     }

    fun getloaderaler(){
        val builder= AlertDialog.Builder(context,R.style.Theme_Transparent1).create()
        val view =  LayoutInflater.from(context).inflate(R.layout.timerclock,null)
        var cancel=view.findViewById<CardView>(R.id.backarrow)
        var choosemodelayout=view.findViewById<LinearLayout>(R.id.choosemodelayout)
        var continuewbutton=view.findViewById<TextView>(R.id.contbutton)
        var englistcharimage=view.findViewById<ImageView>(R.id.englishcharimage)
        var hindicharimage=view.findViewById<ImageView>(R.id.hindicharimage)
        var englisttxt=view.findViewById<TextView>(R.id.englisttxt)
        var hinditxt=view.findViewById<TextView>(R.id.hinditxt)
        var hindicard=view.findViewById<LinearLayout>(R.id.hindicard)
        var englishcard=view.findViewById<LinearLayout>(R.id.englishcard)

        var videoimage=view.findViewById<ImageView>(R.id.videoimage)
        var audioimage=view.findViewById<ImageView>(R.id.audioimage)
        var videotxt=view.findViewById<TextView>(R.id.videotxt)
        var audiotxt=view.findViewById<TextView>(R.id.audiotxt)
        var videocard=view.findViewById<LinearLayout>(R.id.videocard)
        var audiocard=view.findViewById<LinearLayout>(R.id.audiocard)

        choosemodelayout.visibility=View.VISIBLE

        cancel.setOnClickListener {
            builder.dismiss()
        }

        hindicard.setOnClickListener{
            hindicard.setBackgroundResource(R.drawable.selectlanguageborder)
            hinditxt.setTextColor(context.getColor(R.color.red))
            ImageViewCompat.setImageTintList(hindicharimage, ColorStateList.valueOf(context.getColor(R.color.red)))
            englishcard.setBackgroundResource(R.drawable.edittextback)
            englisttxt.setTextColor(context.getColor(R.color.black))
            ImageViewCompat.setImageTintList(englistcharimage, ColorStateList.valueOf(context.getColor(R.color.black)))
        }


        englishcard.setOnClickListener{
            englishcard.setBackgroundResource(R.drawable.selectlanguageborder)
            englisttxt.setTextColor(context.getColor(R.color.red))
            ImageViewCompat.setImageTintList(englistcharimage, ColorStateList.valueOf(context.getColor(R.color.red)))
            hindicard.setBackgroundResource(R.drawable.edittextback)
            hinditxt.setTextColor(context.getColor(R.color.black))
            ImageViewCompat.setImageTintList(hindicharimage, ColorStateList.valueOf(context.getColor(R.color.black)))
        }

        videoimage.setOnClickListener{
            videocard.setBackgroundResource(R.drawable.selectlanguageborder)
            videotxt.setTextColor(context.getColor(R.color.red))
            ImageViewCompat.setImageTintList(videoimage, ColorStateList.valueOf(context.getColor(R.color.red)))
            audiocard.setBackgroundResource(R.drawable.edittextback)
            audiotxt.setTextColor(context.getColor(R.color.black))
            ImageViewCompat.setImageTintList(audioimage, ColorStateList.valueOf(context.getColor(R.color.black)))
        }

        audiocard.setOnClickListener{
            audiocard.setBackgroundResource(R.drawable.selectlanguageborder)
            audiotxt.setTextColor(context.getColor(R.color.red))
            ImageViewCompat.setImageTintList(audioimage, ColorStateList.valueOf(context.getColor(R.color.red)))
            videocard.setBackgroundResource(R.drawable.edittextback)
            videotxt.setTextColor(context.getColor(R.color.black))
            ImageViewCompat.setImageTintList(videoimage, ColorStateList.valueOf(context.getColor(R.color.black)))
        }

        continuewbutton.setOnClickListener {

            var intent= Intent(context, YourAdvocateActivity::class.java)
            context.startActivity(intent)



        }

        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun getloaderalerforcheck(msga:String,msgb:String){
        val builder= AlertDialog.Builder(context,R.style.Theme_Transparent1).create()
        val view =  LayoutInflater.from(context).inflate(R.layout.checkbouncetimerclock,null)
        var cancel=view.findViewById<CardView>(R.id.backarrow)
        var msg1=view.findViewById<TextView>(R.id.msgg)
        var msg2=view.findViewById<TextView>(R.id.msggg)
        var realtivelayout=view.findViewById<RelativeLayout>(R.id.realtivelayout)

        msg1.text=msga
        msg2.text=msgb

        cancel.setOnClickListener {
            builder.dismiss()
        }

        realtivelayout.setOnClickListener {
            var intent= Intent(context, YourAdvocateActivity::class.java)
            context.startActivity(intent)
        }

        builder.setView(view)
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }




 }