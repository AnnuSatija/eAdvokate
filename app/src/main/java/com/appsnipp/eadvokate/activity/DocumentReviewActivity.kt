package com.appsnipp.eadvokate.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.appsnipp.eadvokate.BuildConfig
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.adapter.ServicePackagesAdapter
import com.appsnipp.eadvokate.model.GetReviewDocumentResponseModel
import com.appsnipp.eadvokate.model.PaymentBreakUpModel
import com.appsnipp.eadvokate.model.ServicePackegesModel
import com.appsnipp.eadvokate.model.ServicePackegesResponse
import com.appsnipp.eadvokate.retrofitintrigation.ApiClient
import com.appsnipp.eadvokate.retrofitintrigation.ApiHolder
import com.appsnipp.eadvokate.utils.Constant
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.nagarjunabookstore.utils.OnIntentReceived
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*
import java.util.concurrent.TimeUnit


internal class DocumentReviewActivity : AppCompatActivity() {
    lateinit var backarrow: CardView
    var package_Type = ""
    var sharedpreferences: Sharedpreferences? = null
    lateinit var proceedbutton: TextView
    lateinit var selectfilelayout: LinearLayout
    lateinit var imageUri: Uri
    lateinit var uploadimage: ImageView
    lateinit var uploadfilepath: TextView
    lateinit var noofpages: TextView
    lateinit var imageviewcard: CardView
    lateinit var uploaddoclayout: LinearLayout
    lateinit var basiclayout: LinearLayout
    lateinit var advancelayout: LinearLayout
    lateinit var prolayout: LinearLayout
    lateinit var basictxt: TextView
    lateinit var advancetxt: TextView
    lateinit var protxt: TextView
    lateinit var basicexptxt: TextView
    lateinit var basicexpcharge: TextView
    lateinit var advncexp: TextView
    lateinit var advexpcharge: TextView
    lateinit var proexptxt: TextView
    lateinit var proexpcharge: TextView
    lateinit var basicview: View
    lateinit var advview: View
    lateinit var proview: View
    var accessToken = ""
    lateinit var apiHolder: ApiHolder
    lateinit var packagerecyclerview: RecyclerView
    lateinit var documentmethod: OnIntentReceived
    lateinit var filePath: String
    lateinit var filedatauri: Uri
    lateinit var Uploadfiledatauri: Uri
    val CREATE_FILE = 1
    lateinit var packageId: String
    lateinit var TXT_PLAIN: String

    var packageList: MutableList<ServicePackegesResponse> = arrayListOf()
    lateinit var servicepackageadapter: ServicePackagesAdapter

    val permissions1 = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var permissionsFor13 = arrayOf(
        Manifest.permission.READ_MEDIA_AUDIO,
    )

    lateinit var rQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        loadlocale()
        setContentView(R.layout.activity_document_review)

        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Snackbar.make(findViewById(android.R.id.content),
                    "Permission needed!",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Settings", View.OnClickListener() {
                        try {
                            val uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                            val intent = Intent(
                                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                uri
                            )
                            startActivity(intent)
                        } catch (ex: Exception) {
                            val intent = Intent()
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                            startActivity(intent)
                        }
                    })
                    .show()
            }
        }
        init()
        HitApiforPackages()
        setclicklistner()
    }

    fun init() {
        apiHolder = ApiClient.getApiClient(Constant.Authusername, resources.getString(R.string.authpassword)).create(ApiHolder::class.java)

        sharedpreferences = Sharedpreferences.getInstance(this)
        accessToken = sharedpreferences!!.getAccessToken("connect_sid")
        backarrow = findViewById(R.id.backarrow)
        // first page content.............................................
        proceedbutton = findViewById(R.id.proceedbutton)
        uploaddoclayout = findViewById(R.id.uploaddoclayout)
        selectfilelayout = findViewById(R.id.selectfilelayout)
        imageviewcard = findViewById(R.id.imageviewcard)
        uploadfilepath = findViewById(R.id.uploadfilepath)
        uploadimage = findViewById(R.id.uploadimage)
        noofpages = findViewById(R.id.noofpages)
        proceedbutton.visibility = View.VISIBLE
        uploaddoclayout.visibility = View.VISIBLE
        //...................................................................

        packagerecyclerview = findViewById(R.id.packagerecyclerview)
        packagerecyclerview.layoutManager = LinearLayoutManager(this@DocumentReviewActivity)

        // Experience selection content........................................
    }

    fun setclicklistner() {
        backarrow.setOnClickListener {
            finish()
        }

        proceedbutton.setOnClickListener {
            val packagePosition = sharedpreferences!!.getPosition("packageposition")
            if (noofpages.text.isNullOrEmpty() || noofpages.text.isNullOrBlank() || packagePosition == -1) {
                if (noofpages.text.isNullOrEmpty() || noofpages.text.isNullOrBlank()) {
                    Toast.makeText(this, "Please ,select document for upload!!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (packagePosition == -1) {
                    Toast.makeText(this, "Please select Package !!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                if (packageList.size > 0) {
                    package_Type = packageList[packagePosition!!].id.toString()
                    OkHttpHandler().execute()
                } else {
                    Toast.makeText(this, "Package is missing!!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        selectfilelayout.setOnClickListener {
//            val builder = AlertDialog.Builder(this,R.style.full_screen_dialog).create()
//            val view = layoutInflater.inflate(R.layout.selectfilealert,null)
//            val cancel=view.findViewById<TextView>(R.id.accept)
//            val camera=view.findViewById<LinearLayout>(R.id.cameralayout)
//            val gallery=view.findViewById<LinearLayout>(R.id.gallerylayout)
//            val document=view.findViewById<LinearLayout>(R.id.document)
//
//            builder.setView(view)
//
//            cancel.setOnClickListener {
//                builder.dismiss()
//            }
//
//            camera.setOnClickListener {
//                if(checkPermissions()){
//                    builder.dismiss()
//                    var fileName= "Img_"+Calendar.getInstance().timeInMillis+".jpg"
//                    var f = File(createfile(), fileName)
//                    try {
//                        f.createNewFile()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                    imageUri = FileProvider.getUriForFile(applicationContext, BuildConfig.APPLICATION_ID + ".provider", f)
//                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//                    startActivityForResult(takePicture, 1)
//                }
//            }
//
//            gallery.setOnClickListener {
//                if(checkPermissions()) {
//                    builder.dismiss()
//                    val intent = Intent()
//                    intent.type = "image/*"
//                    intent.action = Intent.ACTION_PICK
//                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 2)
//                }
//            }
//
//            builder.setCanceledOnTouchOutside(false)
//            builder.show()
//
//            document.setOnClickListener {
//                builder.dismiss()
//                val intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.addCategory(Intent.CATEGORY_OPENABLE)
//                intent.type = "*/*"
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), 3)
//            }

            if (checkPermissions()) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "application/pdf"
                startActivityForResult(Intent.createChooser(intent, "Choose a file"), 3)
            } else {

            }


        }

    }

    fun loadlocale() {
        sharedpreferences = Sharedpreferences.getInstance(applicationContext)
        val language: String = sharedpreferences!!.getlanguage("MyLanguage").toString()
        setLocale(language)
    }

    fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        applicationContext.resources.updateConfiguration(
            configuration,
            applicationContext.resources.displayMetrics
        )
        sharedpreferences!!.setlanguage("MyLanguage", lang)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null) {
            if (requestCode == 1) {
                // adding imageuri in array
                val photo = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageviewcard.visibility = View.VISIBLE
                uploadfilepath.visibility = View.GONE
                uploadimage.setImageBitmap(photo)

            } else if (requestCode == 2) {
                val bitmap: Bitmap
                imageUri = data!!.data!!
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    imageviewcard.visibility = View.VISIBLE
                    uploadfilepath.visibility = View.GONE
                    uploadimage.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                filedatauri = data!!.data!!
                val name = getRealPathName(filedatauri)
                Log.d("datavalue", filedatauri.toString())
                imageviewcard.visibility = View.GONE
                uploadfilepath.visibility = View.VISIBLE
                uploadfilepath.text = name
                if (!filedatauri.path.isNullOrEmpty()) {
                    sharedpreferences!!.setData("upload_file_uri", filedatauri.toString())
                    pdfPageCount(getFile(this, filedatauri)!!)
                }
            }
        }

    }

    fun getFile(context: Context, uri: Uri?): File? {
        val destinationFilename = File(context.filesDir.path + File.separatorChar + queryName(context, uri!!))
        try {
            context.contentResolver.openInputStream(uri).use { ins -> createFileFromStream(ins!!, destinationFilename) }
        } catch (ex: Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
        return destinationFilename
    }

    fun createFileFromStream(ins: InputStream, destination: File?) {
        try {
            FileOutputStream(destination).use { os ->
                val buffer = ByteArray(4096)
                var length: Int
                while (ins.read(buffer).also { length = it } > 0) {
                    os.write(buffer, 0, length)
                }
                os.flush()
            }
        } catch (ex: java.lang.Exception) {
            Log.e("Save File", ex.message!!)
            ex.printStackTrace()
        }
    }

    fun queryName(context: Context, uri: Uri): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        returnCursor.close()
        return name
    }

    fun pdfPageCount(pdffilepath: File) {
        try {
            val parcelFileDescriptor =
                ParcelFileDescriptor.open(pdffilepath, ParcelFileDescriptor.MODE_READ_ONLY)
            val pdfRenderer = PdfRenderer(parcelFileDescriptor)
            noofpages.text = pdfRenderer.pageCount.toString()
            pdfRenderer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }


    fun HitApiforPackages() {
        Constant.loadingpopup(this@DocumentReviewActivity, "Loading", "", false)
        val call = apiHolder.GetServicePackages(Constant.HeadersWithAccess(accessToken))
        call.enqueue(object : Callback<ServicePackegesModel> {
            override fun onResponse(call: Call<ServicePackegesModel>?, response: Response<ServicePackegesModel>?) {
                Constant.StopLoader()
                if (response!!.isSuccessful) {
                    val getdata = response.body()
                    val errorcode = getdata!!.status
                    if (errorcode == 200) {
                        packageList = getdata.response
                        if (packageList.size > 0) {
                            setPackageAdapter(packageList)
                        }
                    }
                } else {

                }

            }

            override fun onFailure(call: Call<ServicePackegesModel>?, t: Throwable?) {


            }

        })

    }

    fun setPackageAdapter(packageList: MutableList<ServicePackegesResponse>) {
        servicepackageadapter = ServicePackagesAdapter(this, packageList)
        packagerecyclerview.adapter = servicepackageadapter
        servicepackageadapter.notifyDataSetChanged()
    }

    @SuppressLint("Range")
    fun getRealPathName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor: Cursor = applicationContext.contentResolver.query(uri, null, null, null, null)!!
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }

        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }


    inner class OkHttpHandler : AsyncTask<Any?, Any?, String?>() {
        val client = OkHttpClient().newBuilder().connectTimeout(300, TimeUnit.SECONDS).readTimeout(300, TimeUnit.SECONDS).writeTimeout(300, TimeUnit.SECONDS)
            .build()

        override fun onPreExecute() {
            super.onPreExecute()
            Constant.loadingpopup(this@DocumentReviewActivity, "Loading Data", "", false)
        }

        override fun doInBackground(vararg params: Any?): String? {

            try {
                val request = HitApiForUploadDocumentForReview(package_Type)
                val response = client.newCall(request).execute()
                Log.d("OKHTTPRESPONSE",response.toString())
                return response.body()?.string()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return "Failed"
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            var gson = Gson()
            var reviewDocumentModel = gson.fromJson(s, GetReviewDocumentResponseModel::class.java)
            if (reviewDocumentModel.status == 200) {
                noofpages.text=""
                sharedpreferences!!.savePosition("packageposition", -1)
                HitApiForPaymentBreakUp(reviewDocumentModel.response.id)
                if(packageList.size>0){
                    setPackageAdapter(packageList)
                }
            }
        }
    }


    fun HitApiForUploadDocumentForReview(selectPackage: String): Request {

         //  val file = File(PathUtils.getPath(filedatauri, this@DocumentReviewActivity))
        val file = getFile(this, filedatauri)
        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("package_type", selectPackage)
            .addFormDataPart("request_from", "APP")
            .addFormDataPart("file", file?.name, RequestBody.create(MediaType.parse("application/octet-stream"), file))
            .build()

        val request: Request = Request.Builder()
            .url("https://api.eadvokate.com/customers/review-document/pre-cases-order")
            .method("POST", body)
            .addHeader("X-API-KEY", Constant.X_API_KEY)
            .addHeader("x-access-token", accessToken)
            .addHeader("Authorization", "Basic ZWFkdm9rYXRlOkl3d0s1JDdUJHE2N1pueSQ0NkA1U2ppMDhrakl4b0huOSVRaklOdkVKJmppNGheRUw=")
            .build()

        return request
    }

    fun HitApiForPaymentBreakUp(id: Int) {
        var call = apiHolder.GetPaymentBreakUpAfterUploadDocument(Constant.HeadersWithAccess(accessToken), id)
        call.enqueue(object : Callback<PaymentBreakUpModel> {
            override fun onResponse(
                call: Call<PaymentBreakUpModel>,
                response: Response<PaymentBreakUpModel>) {
                Constant.StopLoader()
                if (response.isSuccessful) {
                    var errorcode = response.body()!!.status
                    if (errorcode == 200) {
                        var intent = Intent(
                            this@DocumentReviewActivity, PaymentBreakupDetailsForUploadDocumentReviewActivity::class.java)
                        var paymentDetails=response.body()!!.response
                        intent.putExtra("total_pages", paymentDetails.total_pages)
                        intent.putExtra("price_per_page", paymentDetails.price_per_page)
                        intent.putExtra("price_before_gst", paymentDetails.price_before_gst)
                        intent.putExtra("gst", paymentDetails.gst)
                        intent.putExtra("total", paymentDetails.total)
                        intent.putExtra("order_id", paymentDetails.order_id)
                        intent.putExtra("order_hash", paymentDetails.order_hash)
                        startActivity(intent)
                    }
                }
                else{

                }
            }
            override fun onFailure(call: Call<PaymentBreakUpModel>, t: Throwable) {

            }
        })
    }


    fun getFileDataFromDrawable(context: Context, uri: Uri?): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        try {
            val iStream = context.contentResolver.openInputStream(uri!!)
            val bufferSize = 1024 * 10
            val buffer = ByteArray(bufferSize)
            var len = 0
            if (iStream != null) {
                while (iStream.read(buffer).also { len = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, len)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArrayOutputStream.toByteArray()
    }

    fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (p in permissionsFor13) {
                result = ActivityCompat.checkSelfPermission(this@DocumentReviewActivity, p!!)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p!!)
                }
            }
        } else {
            for (p in permissions1) {
                result = ActivityCompat.checkSelfPermission(this@DocumentReviewActivity, p!!)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p!!)
                }
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this@DocumentReviewActivity,
                listPermissionsNeeded.toTypedArray(),
                100
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0])
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "application/pdf"
            startActivityForResult(Intent.createChooser(intent, "Choose a file"), 3)
        }
    }


}
