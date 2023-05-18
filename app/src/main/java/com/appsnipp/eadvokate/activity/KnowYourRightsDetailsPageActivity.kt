package com.appsnipp.eadvokate.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.cardview.widget.CardView
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.databinding.ActivityKnowYourRightsDetailsPageBinding
import com.appsnipp.eadvokate.utils.Sharedpreferences
import java.util.*

class KnowYourRightsDetailsPageActivity : AppCompatActivity() {

    var sharedpreferences: Sharedpreferences? = null
    lateinit  var backarrow: CardView
    lateinit var binding :ActivityKnowYourRightsDetailsPageBinding
     var details:String=""
     var title:String=""
     var image:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        binding=ActivityKnowYourRightsDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentValue()
        init()
        setclicklistner()

    }

    fun getIntentValue(){
        if(intent.hasExtra("image")){
            image=intent.getIntExtra("image",0)
        }
        if(intent.hasExtra("detail")){
            details=intent.getStringExtra("detail") as String
        }
        if(intent.hasExtra("title")){
            title=intent.getStringExtra("title") as String
        }
    }

    fun init(){
        binding.title.text=title
        binding.details.text= details
        binding.image.setImageResource(image)
        binding.headtitle.text="Do You Know"
    }

    fun setclicklistner(){
        binding.backarrow.setOnClickListener {
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