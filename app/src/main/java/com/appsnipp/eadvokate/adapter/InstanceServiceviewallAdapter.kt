package com.appsnipp.eadvokate.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.provider.ContactsContract.Profile
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.*
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.ServicesResponse
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class InstanceServiceviewallAdapter(val context: Context, val serviceList:MutableList<ServicesResponse>, val categoriesList: List<InstanceServiceModel>, val type:String,val callingType:String=""):RecyclerView.Adapter<InstanceServiceviewallAdapter.ViewHolder>(){

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstanceServiceviewallAdapter.ViewHolder {
             val view = LayoutInflater.from(parent.context).inflate(R.layout.whatsissueitemlayout, parent, false)
         return ViewHolder(view)
     }

     override fun onBindViewHolder(holder: InstanceServiceviewallAdapter.ViewHolder, position: Int) {
         if(type.equals("instance")){
             holder.instancelayout.visibility=View.VISIBLE
             holder.whatsnewlayout.visibility=View.GONE
             holder.trendingadvocatelayout.visibility=View.GONE
             holder.textView.text=serviceList.get(position).service_name
             Glide.with(context).load(serviceList.get(position).image).into( holder.imageView)
             holder.itemView.setOnClickListener {
                     if(serviceList[position].service_name == context.getString(R.string.revdocmsg))
                     {
                         context.startActivity(Intent(context,DocumentReviewActivity::class.java))
                     }
                     else{
                         var serviceType=serviceList[position].service_type
                         var serviceId=serviceList[position].id
                         var intent=Intent(context, ForVideoAudioConsultationActivity::class.java)
                         intent.putExtra("service_type", serviceType)
                         intent.putExtra("service_id", serviceId)
                         if(!callingType.isNullOrBlank()){
                          intent.putExtra("callingtype",callingType)
                         }
                         context.startActivity(intent)
                 }
             }
         }
         else if(type.equals("trendadvocate")){
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.GONE
             holder.pinglayout.visibility=View.VISIBLE
             holder.trendingadvocatelayout.visibility=View.VISIBLE
             holder.advocatename.setText(categoriesList.get(position).name)
             holder.advocateimage.setImageResource(categoriesList.get(position).image)
             holder.exp.setText(categoriesList.get(position).experience)
             holder.courtname.setText(categoriesList.get(position).courtname)
             holder.viewprofile.setOnClickListener{
                 var intent=Intent(context,LawyerProfileActivity::class.java)
                 context.startActivity(intent)
             }
             holder.profilecontibutton.setOnClickListener {
                 var intent=Intent(context,ScheduleAppointmentActivity::class.java)
                 context.startActivity(intent)
             }
         }
         else if(type.equals("myadvocate")){
             holder.instancelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.GONE
             holder.pinglayout.visibility=View.GONE
             holder.trendingadvocatelayout.visibility=View.VISIBLE
             holder.advocatename.setText(categoriesList.get(position).name)
             holder.advocateimage.setImageResource(categoriesList.get(position).image)
             holder.viewprofile.setOnClickListener{
                 var intent=Intent(context,LawyerProfileActivity::class.java)
                 context.startActivity(intent)
             }
             holder.profilecontibutton.setOnClickListener {
                 var intent=Intent(context,ScheduleAppointmentActivity::class.java)
                 context.startActivity(intent)
             }
         }
         else
         {
             holder.instancelayout.visibility=View.GONE
             holder.trendingadvocatelayout.visibility=View.GONE
             holder.whatsnewlayout.visibility=View.VISIBLE
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
         else return serviceList.size
     }

     class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val imageView: ImageView = itemView.findViewById(R.id.catimage)
         val textView: TextView = itemView.findViewById(R.id.catname)
         val whatsnewlayout: CardView = itemView.findViewById(R.id.whatsnewlayout)
         val instancelayout: CardView = itemView.findViewById(R.id.instancelayout)
         val trendingadvocatelayout: CardView = itemView.findViewById(R.id.trendingadvocatelayout)
         val imageView1: ImageView = itemView.findViewById(R.id.whatsimage)
         val advocateimage: ImageView = itemView.findViewById(R.id.advocateimage)
         val pinadvocateimage: ImageView = itemView.findViewById(R.id.pinadvocateimage)
         val advocatename: TextView = itemView.findViewById(R.id.advocatename)
         val exp: TextView = itemView.findViewById(R.id.exp)
         val courtname: TextView = itemView.findViewById(R.id.courtname)
         val viewprofile: TextView = itemView.findViewById(R.id.viewprofile)
         val profilecontibutton: TextView = itemView.findViewById(R.id.profilecontibutton)
         val textView1: TextView = itemView.findViewById(R.id.whatsname)
         val pinglayout: LinearLayout = itemView.findViewById(R.id.pinglayout)

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

        choosemodelayout.visibility=View.GONE

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

}