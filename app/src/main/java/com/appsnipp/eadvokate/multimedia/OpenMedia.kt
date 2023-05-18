package com.appsnipp.eadvokate.multimedia

import android.content.Context
import android.media.SoundPool
import android.media.SoundPool.OnLoadCompleteListener
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.appsnipp.eadvokate.databinding.ActivityOpenMediaBinding
import com.appsnipp.eadvokate.utils.Constant
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class OpenMedia : AppCompatActivity(),CoroutineScope by MainScope() {
    lateinit var binding :ActivityOpenMediaBinding
    lateinit var url:String
    lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOpenMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getdataFromIntent()
        binding.backarrow.setOnClickListener {
            finish()
        }

    }
       fun getdataFromIntent(){
           if(intent.hasExtra("account")){
               binding.webview.visibility= View.VISIBLE
               binding.pdfviewer.visibility=View.GONE
               if(intent.hasExtra("URL")){
                   url=intent.getStringExtra("URL") as String
               }
               if(intent.hasExtra("Title")){
                   title=intent.getStringExtra("Title") as String
               }
               binding.title.text=title
               setWebView(url)
           }else{
               binding.webview.visibility= View.GONE
               binding.pdfviewer.visibility=View.VISIBLE
               if(intent.hasExtra("title")){
                   title=intent.getStringExtra("title")as String
               }
               if(intent.hasExtra("url")){
                   url=intent.getStringExtra("url")as String
               }
               binding.title.text=title
               RetrievePDFFromURL(this@OpenMedia,binding.pdfviewer).execute(url)
           }

       }



    class RetrievePDFFromURL(val context: Context,val pdfView:PDFView) : AsyncTask<String, Void, InputStream>(),com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener {

        override fun onPreExecute() {
            Constant.loadingpopup(context, "Loading","",false)
        }

        // on below line we are calling our do in background method.
        override fun doInBackground(vararg params: String?): InputStream? {
            // on below line we are creating a variable for our input stream.
            var inputStream: InputStream? = null
            try {

                val url = URL(params.get(0))
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }
            // on below line we are adding catch block to handle exception
            catch (e: Exception) {
                e.printStackTrace()
                return null;
            }

            return inputStream;
        }

        override fun onPostExecute(result: InputStream?) {
            pdfView.fromStream(result).swipeHorizontal(false).onLoad(this).load()
        }


        override fun loadComplete(nbPages: Int) {
            Constant.StopLoader()
        }


    }


    fun setWebView(url:String){
        binding.webview.settings.javaScriptEnabled=true
        binding.webview.webViewClient= WebViewClient()
        binding.webview.settings.supportZoom()
        binding.webview.loadUrl(url)

    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webview.canGoBack())
            binding.webview.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

}
