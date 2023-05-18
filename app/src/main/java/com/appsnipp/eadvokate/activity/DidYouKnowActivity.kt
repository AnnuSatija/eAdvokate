package com.appsnipp.eadvokate.activity

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat.NestedScrollType
import androidx.core.widget.NestedScrollView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class DidYouKnowActivity: AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit  var knowyourrightslayout: NestedScrollView
    lateinit  var didyouknowlayout: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_know_your_rights_details_page)

        init()
        setclicklistner()
    }

    fun init(){
        backarrow=findViewById(R.id.backarrow)
        knowyourrightslayout=findViewById(R.id.knowyourrightslayout)
        didyouknowlayout=findViewById(R.id.didyouknowlayout)
        knowyourrightslayout.visibility=View.GONE
        didyouknowlayout.visibility=View.VISIBLE
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