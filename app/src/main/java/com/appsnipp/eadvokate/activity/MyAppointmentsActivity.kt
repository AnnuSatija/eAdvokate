package com.appsnipp.eadvokate.activity

import android.content.res.ColorStateList
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class MyAppointmentsActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var upcominglayout: LinearLayout
    lateinit  var previouslayout: LinearLayout
    lateinit  var upcominglistlayout: LinearLayout
    lateinit  var previouslistlayout: LinearLayout
    lateinit  var upcomingtxt: TextView
    lateinit  var previoustxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_my_appointments)

        init()
        setclicklistner()

    }

    fun  init(){
        backarrow=findViewById(R.id.backarrow)
        upcominglayout=findViewById(R.id.upcominglayout)
        previouslayout=findViewById(R.id.previouslayout)
        upcominglistlayout=findViewById(R.id.upcominglistlayout)
        previouslistlayout=findViewById(R.id.previouslistlayout)
        upcomingtxt=findViewById(R.id.upcomingtxt)
        previoustxt=findViewById(R.id.previoustxt)

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }

        upcominglayout.setOnClickListener{
            upcominglayout.setBackgroundResource(R.drawable.signbutton)
            upcomingtxt.setTextColor(applicationContext.getColor(R.color.white))
            previouslayout.setBackgroundResource(R.drawable.selectlanguageborder)
            previoustxt.setTextColor(applicationContext.getColor(R.color.red))
            upcominglistlayout.visibility=View.VISIBLE
            previouslistlayout.visibility=View.GONE
        }


        previouslayout.setOnClickListener {
            previouslayout.setBackgroundResource(R.drawable.signbutton)
            previoustxt.setTextColor(applicationContext.getColor(R.color.white))
            upcominglayout.setBackgroundResource(R.drawable.selectlanguageborder)
            upcomingtxt.setTextColor(applicationContext.getColor(R.color.red))
            upcominglistlayout.visibility = View.GONE
            previouslistlayout.visibility = View.VISIBLE
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
}