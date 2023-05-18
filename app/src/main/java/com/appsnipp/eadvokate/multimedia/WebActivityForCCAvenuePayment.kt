package com.appsnipp.eadvokate.multimedia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.appsnipp.eadvokate.databinding.ActivityWebForCcavenuePaymentBinding
import com.appsnipp.eadvokate.utils.AvenuesParams
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.view.activity.SuccessFauilerMsgActivity
import java.net.URLEncoder

class WebActivityForCCAvenuePayment : AppCompatActivity() {
       lateinit var haskey:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val  binding=ActivityWebForCcavenuePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("order_hash")){
            haskey=intent.getStringExtra("order_hash")as String
        }

        ReviewWebView(this@WebActivityForCCAvenuePayment,binding,haskey).execute()

    }

    class ReviewWebView(val context: Context,val binding: ActivityWebForCcavenuePaymentBinding,val hashkey:String): AsyncTask<Void, Void, Void>(){


        override fun onPreExecute() {
            super.onPreExecute()
            Constant.loadingpopup(context,"Loading","",false)
        }


        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Constant.StopLoader()
            class MyJavaScriptInterface{
                @JavascriptInterface
                fun processHTML(html: String) {
                    // process the html source code to get final status of transaction
                    var status: String? = null
                    status = if (html.indexOf("Failure") != -1) {
                        "The transaction has been failed."
                    } else if (html.indexOf("Success") != -1) {
                        "Your transaction is successful."
                    } else if (html.indexOf("Aborted") != -1) {
                        "The transaction has been cancelled."
                    } else {
                        "Status Not Known!"
                    }
                    //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Log.e("STATUS OF THE TRANS: ", html)
                    val intent = Intent(context, SuccessFauilerMsgActivity::class.java)
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("transStatus", status)
                    context.startActivity(intent)
                }
            }
            binding.webview.settings.javaScriptEnabled=true
            binding.webview.settings.javaScriptEnabled=true
            binding.webview.addJavascriptInterface(MyJavaScriptInterface(),"HTMLOUT")
            binding.webview.webViewClient=object:WebViewClient(){

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Constant.loadingpopup(context,"Loading","",false)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Constant.StopLoader()
                    if (url!!.indexOf("/review-doc-pg-response") != -1) {  //https://api.eadvokate.com/payments/review-doc-pg-response
                        binding.webview.visibility = View.GONE
                        binding.webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
                        println("Im in URL INDEX OF")
                    }

                }
            }

            // for testing purpose..........................................................................
            val postData: String = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(Constant.ACCESS_CODE,
                "UTF-8") + "&" + AvenuesParams.RSAREQUEST + "=" + URLEncoder.encode(hashkey, "UTF-8")
            binding.webview.postUrl(Constant.TRANS_URL, postData.toByteArray())

        }

        override fun doInBackground(vararg params: Void?): Void? {
           return  null
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@WebActivityForCCAvenuePayment, SuccessFauilerMsgActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("transStatus", "Fail")
        startActivity(intent)
    }

}