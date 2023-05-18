package com.appsnipp.eadvokate.activity

import android.content.res.Configuration
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class PrivacyPolicyActivity:AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var helpcenterlayout: RelativeLayout
    lateinit  var privacylayout: RelativeLayout
    lateinit  var title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_help_center)

        init()
        setclicklistner()
    }

    fun  init(){
        backarrow=findViewById(R.id.backarrow)
        helpcenterlayout=findViewById(R.id.helpcenterlayout)
        privacylayout=findViewById(R.id.privacylayout)
        title=findViewById(R.id.title)
        helpcenterlayout.visibility=View.GONE
        privacylayout.visibility=View.VISIBLE
        title.text=applicationContext.getString(R.string.privacytitle)

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
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