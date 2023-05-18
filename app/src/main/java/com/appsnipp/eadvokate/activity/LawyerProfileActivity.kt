package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class LawyerProfileActivity : AppCompatActivity() {
    lateinit var backarrow: CardView
    lateinit var contibutton: TextView
    var sharedpreferences: Sharedpreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_lawyer_profile)
        init()
        setclicklistner()
    }
    fun init(){
        backarrow=findViewById(R.id.backarrow)
        contibutton=findViewById(R.id.contibutton)

    }
    fun setclicklistner() {
        backarrow.setOnClickListener { finish() }
        contibutton.setOnClickListener {
            var intent=Intent(this@LawyerProfileActivity,ScheduleAppointmentActivity::class.java)
            startActivity(intent)
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