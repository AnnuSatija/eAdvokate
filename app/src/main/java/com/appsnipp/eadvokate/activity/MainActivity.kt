package com.appsnipp.eadvokate.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.fragment.Account
import com.appsnipp.eadvokate.fragment.Explore
import com.appsnipp.eadvokate.fragment.Feeds
import com.appsnipp.eadvokate.fragment.Home
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.viewmodel.ViewModelClass
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import java.io.File
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    var sharedpreferences: Sharedpreferences? = null
    lateinit var  bottomNavigationView: BottomNavigationView
    var Count:Int=1
    lateinit var viewModelClass: ViewModelClass
    private var appUpdateManager: AppUpdateManager? = null
    private var installStateUpdatedListener: InstallStateUpdatedListener? = null
    var UPDATE_CODE = 22
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadlocale()
        setContentView(R.layout.activity_main)

        init()
        navigationclicklistner()
    }

    fun init(){
        viewModelClass=ViewModelProviders.of(this).get(ViewModelClass::class.java)
        bottomNavigationView=findViewById(R.id.bottomNavigationView)
    }

    fun navigationclicklistner(){
        val Home=Home()
        val Explore= Explore()
        val Profile= Feeds()
        val Helpp= Account()
        setCurrentFragment(Home)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(Home)
                // if user did not login then first goto login page
                R.id.explore ->setCurrentFragment(Explore)
                R.id.profile -> setCurrentFragment(Profile)
                R.id.help -> setCurrentFragment(Helpp)
            }
            true
        }
    }

    override fun onBackPressed() {
        var  bottomNavigationView=findViewById(R.id.bottomNavigationView) as BottomNavigationView
        val seletedItemId = bottomNavigationView.selectedItemId
        if (R.id.home !== seletedItemId) {
            setHomeItem(this@MainActivity)
        } else {
            if (Count == 2) {
                finish()
            } else {
                Count = 2
                Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    Count = 1
                }, 2000)
            }
        }
    }


    fun loadlocale() {
        sharedpreferences = Sharedpreferences.getInstance(applicationContext)
        val language: String = sharedpreferences!!.getlanguage("MyLanguage").toString()
        setLocale(language)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


    fun setHomeItem(activity: Activity) {
        val bottomNavigationView = activity.findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        applicationContext.resources.updateConfiguration(configuration, applicationContext.resources.displayMetrics)
        sharedpreferences!!.setlanguage("MyLanguage", lang)
    }

    override fun onStart() {
        super.onStart()
        installStateUpdatedListener =
            InstallStateUpdatedListener { installState ->
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                    popupSnackbarForCompleteUpdate()
                } else if (installState.installStatus() == InstallStatus.INSTALLED) {
                    if (appUpdateManager != null) {
                        appUpdateManager!!.unregisterListener(installStateUpdatedListener!!)
                    }
                } else {
                    Log.i(
                        ContentValues.TAG,
                        "InstallStateUpdatedListener: state: " + installState.installStatus()
                    )
                }
            }
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager!!.registerListener(installStateUpdatedListener!!)
        appUpdateManager!!.getAppUpdateInfo().addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)
            ) {
                try {
                    appUpdateManager!!.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, this@MainActivity, UPDATE_CODE)

                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate()
            } else {
                Log.e(ContentValues.TAG, "checkForAppUpdateAvailability: something else")
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "New app is ready!",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("Install") { view: View? ->
            if (appUpdateManager != null) {
                appUpdateManager!!.completeUpdate()
            }
        }
        snackbar.setActionTextColor(resources.getColor(R.color.red))
        snackbar.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e(ContentValues.TAG, "onActivityResult: app download failed")
                Toast.makeText(
                    this,
                    "App Updation Failed,Please Update Your App From Play Store",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this@MainActivity,"App is going to update .Please wait for few minutes !!",Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (appUpdateManager != null) {
            appUpdateManager!!.unregisterListener(installStateUpdatedListener!!)
        }
    }


}