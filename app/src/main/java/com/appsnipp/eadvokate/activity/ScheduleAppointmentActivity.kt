package com.appsnipp.eadvokate.activity

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.appsnipp.eadvokate.BuildConfig
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.utils.Constant
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduleAppointmentActivity : AppCompatActivity() {
    lateinit var backarrow: CardView
    lateinit var imageviewcard: CardView
    lateinit var title: TextView
    lateinit var nearbyadvoactelayout: RelativeLayout
    lateinit var searchadvoactelayout: RelativeLayout
    lateinit var searchadvoacteaddresslayout: RelativeLayout
    lateinit var scheduleappointmentlayout: RelativeLayout
    lateinit var onlinevideolayout: RelativeLayout
    lateinit var onlineaudiolayout: RelativeLayout
    lateinit var yesterdaydate: TextView
    lateinit var todaydate: TextView
    lateinit var tomorrowdate: TextView
    lateinit var proceedcheckout: TextView
    lateinit var videotxt: TextView
    lateinit var videotxtfee: TextView
    lateinit var audiotxt: TextView
    lateinit var audiofee: TextView
    lateinit var opencalendertxt: TextView
    lateinit var selectfilelayout: LinearLayout
    lateinit var imageUri: Uri
    lateinit var uploadimage: ImageView
    lateinit var uploadfilepath: TextView
    lateinit var  current:String

    var permissions:Array<String> = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_advocate)
        init()
        setclicklistner()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun init(){
        nearbyadvoactelayout=findViewById(R.id.nearbyadvoactelayout)
        searchadvoactelayout=findViewById(R.id.searchadvoactelayout)
        searchadvoacteaddresslayout=findViewById(R.id.searchadvoacteaddresslayout)
        scheduleappointmentlayout=findViewById(R.id.scheduleappointmentlayout)
        onlinevideolayout=findViewById(R.id.onlinevideolayout)
        onlineaudiolayout=findViewById(R.id.onlineaudiolayout)
        yesterdaydate=findViewById(R.id.yesterdaydate)
        todaydate=findViewById(R.id.todaydate)
        tomorrowdate=findViewById(R.id.tomorrowdate)
        proceedcheckout=findViewById(R.id.proceedcheckout)
        selectfilelayout=findViewById(R.id.selectfilelayout)
        videotxt=findViewById(R.id.videotxt)
        videotxtfee=findViewById(R.id.videotxtfee)
        audiotxt=findViewById(R.id.audiotxt)
        audiofee=findViewById(R.id.audiofee)
        opencalendertxt=findViewById(R.id.opencalendertxt)
        backarrow=findViewById(R.id.backarrow)
        imageviewcard=findViewById(R.id.imageviewcard)
        uploadfilepath=findViewById(R.id.uploadfilepath)
        uploadimage=findViewById(R.id.uploadimage)
        title=findViewById(R.id.title)
        searchadvoactelayout.visibility= View.GONE
        nearbyadvoactelayout.visibility= View.GONE
        searchadvoacteaddresslayout.visibility= View.GONE
        scheduleappointmentlayout.visibility= View.VISIBLE
        title.text=applicationContext.getString(R.string.scheduleappnt)

        // getting current date
        val formatter = DateTimeFormatter.ofPattern("dd MMM")
        val sdf=SimpleDateFormat("dd MMM")
        current= LocalDateTime.now().format(formatter)
        todaydate.text = current

        tomorrowdate.text=sdf.format(Constant.incrementDateByOne(Date()))
        yesterdaydate.text=sdf.format(Constant.decrementDateByOne(Date()))

    }

    fun setclicklistner(){
        backarrow.setOnClickListener {
            finish()
        }

        onlinevideolayout.setOnClickListener{
            onlinevideolayout.setBackgroundResource(R.drawable.selectlanguageborder)
            onlineaudiolayout.setBackgroundResource(R.drawable.edittextback)
            videotxtfee.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            videotxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            audiotxt.setTextColor(Color.BLACK)
            audiofee.setTextColor(Color.BLACK)

        }

        onlineaudiolayout.setOnClickListener{
            onlineaudiolayout.setBackgroundResource(R.drawable.selectlanguageborder)
            onlinevideolayout.setBackgroundResource(R.drawable.edittextback)
            audiotxt.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            audiofee.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            videotxtfee.setTextColor(Color.BLACK)
            videotxt.setTextColor(Color.BLACK)
        }

        yesterdaydate.setOnClickListener{
            yesterdaydate.setBackgroundResource(R.drawable.selectlanguageborder)
            todaydate.setBackgroundResource(R.drawable.edittextback)
            tomorrowdate.setBackgroundResource(R.drawable.edittextback)
            yesterdaydate.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            todaydate.setTextColor(Color.BLACK)
            tomorrowdate.setTextColor(Color.BLACK)
        }

        todaydate.setOnClickListener{
            todaydate.setBackgroundResource(R.drawable.selectlanguageborder)
            yesterdaydate.setBackgroundResource(R.drawable.edittextback)
            tomorrowdate.setBackgroundResource(R.drawable.edittextback)
            todaydate.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            yesterdaydate.setTextColor(Color.BLACK)
            tomorrowdate.setTextColor(Color.BLACK)
        }

        tomorrowdate.setOnClickListener{
            tomorrowdate.setBackgroundResource(R.drawable.selectlanguageborder)
            todaydate.setBackgroundResource(R.drawable.edittextback)
            yesterdaydate.setBackgroundResource(R.drawable.edittextback)
            tomorrowdate.setTextColor(ColorStateList.valueOf(applicationContext.getColor(R.color.red)))
            todaydate.setTextColor(Color.BLACK)
            yesterdaydate.setTextColor(Color.BLACK)
        }

        opencalendertxt.setOnClickListener {

            opencalendar()

        }

        selectfilelayout.setOnClickListener {
            val builder = AlertDialog.Builder(this,R.style.full_screen_dialog).create()
            val view = layoutInflater.inflate(R.layout.selectfilealert,null)
            val cancel=view.findViewById<TextView>(R.id.accept)
            val camera=view.findViewById<LinearLayout>(R.id.cameralayout)
            val gallery=view.findViewById<LinearLayout>(R.id.gallerylayout)
            val document=view.findViewById<LinearLayout>(R.id.document)

            builder.setView(view)

            cancel.setOnClickListener {
                builder.dismiss()
            }
            camera.setOnClickListener {
              if(checkPermissions()){
                  builder.dismiss()
                  var fileName= "Img_"+Calendar.getInstance().timeInMillis+".jpg"
                  var f = File(createfile(), fileName)
                  try {
                      f.createNewFile()
                  } catch (e: IOException) {
                      e.printStackTrace()
                  }
                  imageUri = FileProvider.getUriForFile(applicationContext, BuildConfig.APPLICATION_ID + ".provider", f)
                  val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                  takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                  startActivityForResult(takePicture, 1)
              }
            }

            gallery.setOnClickListener {
                if(checkPermissions()) {
                    builder.dismiss()
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_PICK
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 2)
                }
            }

            document.setOnClickListener {
                builder.dismiss()
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "*/*"
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 3)
            }

            builder.setCanceledOnTouchOutside(false)
            builder.show()

        }

        proceedcheckout.setOnClickListener {

        }
    }

    fun createfile():File?{
        var directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator + "UserProfile")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory

    }

    fun opencalendar(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this@ScheduleAppointmentActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val newDate:Calendar =Calendar.getInstance()
            newDate.set(year, monthOfYear, dayOfMonth)

            val sdf = SimpleDateFormat("dd MMM")
            todaydate.text=sdf.format( newDate.time)
            tomorrowdate.text=sdf.format(Constant.incrementDateByOne(newDate.time))
            yesterdaydate.text=sdf.format(Constant.decrementDateByOne(newDate.time))

        }, year, month, day)

        dpd.show()
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ActivityCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 1)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission: " + permissions[0] + "was " + grantResults[0])
            //resume tasks needing this permission


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            // adding imageuri in array
            val photo = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            imageviewcard.visibility=View.VISIBLE
            uploadfilepath.visibility=View.GONE
            uploadimage.setImageBitmap(photo)
          /*  val baos = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)*/
        }
        else if(requestCode==2){
            val bitmap: Bitmap
            imageUri = data!!.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageviewcard.visibility=View.VISIBLE
                uploadfilepath.visibility=View.GONE
                uploadimage.setImageBitmap(bitmap)
                /*  val baos = ByteArrayOutputStream()
                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                  val b = baos.toByteArray()
                  val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
  */

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        else{
            var filedatauri=data!!.data!!
            var filepath=getRealPathFromURI(this,filedatauri)
            imageviewcard.visibility=View.GONE
            uploadfilepath.visibility=View.VISIBLE
            uploadfilepath.text=filepath

        }


    }

    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        when {
            // DocumentProvider
            DocumentsContract.isDocumentUri(context, uri) -> {
                when {
                    // ExternalStorageProvider
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        // This is for checking Main Memory
                        return if ("primary".equals(type, ignoreCase = true)) {
                            if (split.size > 1) {
                                Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                            } else {
                                Environment.getExternalStorageDirectory().toString() + "/"
                            }
                            // This is for checking SD Card
                        } else {
                            "storage" + "/" + docId.replace(":", "/")
                        }
                    }
                    isDownloadsDocument(uri) -> {
                        val fileName = getFilePath(context, uri)
                        if (fileName != null) {
                            return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                        }
                        var id = DocumentsContract.getDocumentId(uri)
                        if (id.startsWith("raw:")) {
                            id = id.replaceFirst("raw:".toRegex(), "")
                            val file = File(id)
                            if (file.exists()) return id
                        }
                        val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                        return getDataColumn(context, contentUri, null, null)
                    }
                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":").toTypedArray()
                        val type = split[0]
                        var contentUri: Uri? = null
                        when (type) {
                            "image" -> {
                                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            }
                            "video" -> {
                                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            }
                            "audio" -> {
                                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            }
                        }
                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }
                }
            }
            "content".equals(uri.scheme, ignoreCase = true) -> {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            }
            "file".equals(uri.scheme, ignoreCase = true) -> {
                return uri.path
            }
        }
        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun getFilePath(context: Context, uri: Uri?): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        try {
            if (uri == null) return null
            cursor = context.contentResolver.query(uri, projection, null, null,
                null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }


}