package com.appsnipp.eadvokate.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class HelpCenterActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var submit: TextView
    lateinit  var helpcenterlayout: RelativeLayout
    lateinit  var privacylayout: RelativeLayout
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
        submit=findViewById(R.id.submit)
        helpcenterlayout=findViewById(R.id.helpcenterlayout)
        privacylayout=findViewById(R.id.privacylayout)
        helpcenterlayout.visibility= View.VISIBLE
        privacylayout.visibility= View.GONE

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }
        submit.setOnClickListener {
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