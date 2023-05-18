package com.appsnipp.eadvokate.multimedia

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.fragment.FeedbackVideocall
import java.util.ArrayList


class WebViewForVideoMeeting : AppCompatActivity() {
    lateinit var webview: WebView
      var URL:String=""
      var userName:String=""
      var roomId:String=""
      var refNumber:Int =0
     lateinit var backarrow:CardView
     lateinit var title:TextView
     private var MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101
     private var myRequest: PermissionRequest? = null
    var permissions1 = arrayOf(
        Manifest.permission.CAMERA
    )
    val bundle=Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_for_video_meeting)

        getIntentValue()
        init()
    }

    fun getIntentValue(){
          if(intent.hasExtra("room_id"))
             {
              roomId= intent.getStringExtra("room_id").toString()
             }

            if(intent.hasExtra("name"))
            {
            userName= intent.getStringExtra("name").toString()
            }
            refNumber= (0..10).random()

           if(!roomId.isNullOrEmpty()&&!userName.isNullOrEmpty()) {
               URL="https://eadvokate.yourvideo.live/${roomId}?name=${userName.filterNot { it.isWhitespace() }}&user_ref=${refNumber}&video=yes&landing=no&audio=yes&screenshare=yes&group_chat=no&username=no&whiteboard=no&pvt_chat=no&clock=yes&fixed_toolbar=no&room_name=yes&room_lock=no"
               Log.d("URL",URL)
           }

         }

    @SuppressLint("SuspiciousIndentation")
    fun init(){
        webview=findViewById(R.id.webview)
        backarrow=findViewById(R.id.backarrow)
        title=findViewById(R.id.title)
        displayFromAsset(URL)
        var fragment=supportFragmentManager.findFragmentByTag("feedbackfragment")
           if (fragment is FeedbackVideocall) {
               fragment.requireFragmentManager().popBackStack();
               title.text="Feedback"
               backarrow.visibility=View.GONE
               webview.visibility=View.GONE
           }
           else{
               title.text="Meeting Room"
               backarrow.visibility=View.VISIBLE
               webview.visibility=View.VISIBLE
         }

        backarrow.setOnClickListener {
            title.text="Feedback"
            val feedbackfragment= FeedbackVideocall()
            bundle.putString("roomid",roomId)
            feedbackfragment.arguments = bundle
            setCurrentFragment(feedbackfragment)
            backarrow.visibility=View.GONE
            webview.visibility=View.GONE
        }

    }

    private fun displayFromAsset(extensionurl: String) {
        webview.settings.javaScriptEnabled=true
        webview.settings.loadWithOverviewMode = true
        webview.settings.domStorageEnabled = true
        webview.settings.mediaPlaybackRequiresUserGesture=false
        webview.settings.javaScriptCanOpenWindowsAutomatically=true
        webview.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webview.settings.cacheMode=WebSettings.LOAD_NO_CACHE
        webview.settings.pluginState=WebSettings.PluginState.ON
        webview.settings.setSupportZoom(false)
        webview.webViewClient= WebViewClient()
        webview.webChromeClient=object :WebChromeClient(){
            override fun onPermissionRequest(request: PermissionRequest) {
                    myRequest=request
                   if(checkPermissions()) {
                    callRequestForAudioAccessWebView(request)
                  }
            }
        }
        webview.loadUrl(extensionurl)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                Log.d("WebView", "PERMISSION FOR AUDIO")
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        myRequest!!.grant(myRequest!!.resources)
                        webview.loadUrl(URL)
                    }
                }

            }

            else-> callRequestForAudioAccessWebView(myRequest)
        }
    }

    fun askForPermission(origin: String, permission: String, requestCode: Int) {
        Log.d("WebView", "inside askForPermission for" + origin + "with" + permission)
        if (ContextCompat.checkSelfPermission(applicationContext, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@WebViewForVideoMeeting, permission)) {


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this@WebViewForVideoMeeting, arrayOf(permission), requestCode

                )
            }
        } else {
            myRequest!!.grant(myRequest!!.resources)
        }
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions1) {
            result = ContextCompat.checkSelfPermission(this@WebViewForVideoMeeting, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this@WebViewForVideoMeeting,
                listPermissionsNeeded.toTypedArray(),
                100
            )
            return false
        }
        return true
    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webview.canGoBack())
            webview.goBack()
        // if your webview cannot go back
        // it will exit the application
        else {
            val fragment = supportFragmentManager.findFragmentByTag("feedbackfragment")
            if (fragment is FeedbackVideocall) {
                fragment.requireFragmentManager().popBackStack();
                finishAffinity()
            } else {
                title.text = "Feedback"
                backarrow.visibility = View.GONE
                webview.visibility=View.GONE
                val feedbackfragment = FeedbackVideocall()
                bundle.putString("roomid",roomId)
                feedbackfragment.arguments = bundle
                setCurrentFragment(feedbackfragment)
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            add(fragment,"feedbackfragment")
            addToBackStack("feedbackfragment")
            replace(R.id.framelayout1, fragment)
            commit()
        }

    fun callRequestForAudioAccessWebView(request: PermissionRequest?){
        myRequest=request
        for (permission in request!!.resources){
            when(permission){
                "android.webkit.resource.AUDIO_CAPTURE" -> askForPermission(request.origin.toString(), Manifest.permission.RECORD_AUDIO, MY_PERMISSIONS_REQUEST_RECORD_AUDIO)
            }
        }
    }

}
