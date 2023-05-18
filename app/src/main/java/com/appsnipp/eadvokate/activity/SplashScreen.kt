package com.appsnipp.eadvokate.activity

import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SplashScreen : AppCompatActivity() {
     lateinit var imageview:ImageView
    var parentLayout: View? = null
    private val SPLASH_DISPLAY_LENGTH = 1000
    lateinit var sharedpreferences:Sharedpreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)
        sharedpreferences= Sharedpreferences.getInstance(this)
        setLocale("en")
        imageview=findViewById(R.id.logoicon)
        parentLayout = findViewById(android.R.id.content)
        goToMain()

    }

    private fun goToMain() {
        Handler().postDelayed({
            if (isNetworkConnected()) {
                if(sharedpreferences.getlogged("Userlogin")){
                    val intent=Intent(this@SplashScreen,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this@SplashScreen, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            } else {
                checkInternet(parentLayout)
            }
        }, 1000)
    }

    fun checkInternet(parentLayout: View?) {
        Handler().postDelayed({ finish() }, SPLASH_DISPLAY_LENGTH.toLong())
        Snackbar.make(parentLayout!!, "Please Check Internet Connection ", Snackbar.LENGTH_LONG)
            .setAction("CLOSE") { finish() }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .setDuration(1000)
            .show()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
        sharedpreferences?.setlanguage("MyLanguage", lang)
    }


}