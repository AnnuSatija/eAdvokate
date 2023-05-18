package com.appsnipp.eadvokate.utils

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewbinding.BuildConfig
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.model.InstanceServiceModel
import com.appsnipp.eadvokate.model.InstanceServiceOnlyNameModel
import okhttp3.*
import java.math.BigInteger
import java.security.MessageDigest
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Constant {

    companion object {
        var access_token: String = ""
        val BaseUrl = "https://api.eadvokate.com/"
        val Customer_url = "https://api.eadvokate.com/customers/"
        val X_API_KEY = "14f4ac00-0f42-43b9-97db-665f26f19cc0" //Header
        val Authusername = "eadvokate" //Header
        val RefundPolicyURl = "https://eadvokate.com/cancellation-policy"
        val SchoolPackageApi = "school-packages/"
        val SchoolPackageInfo = "package-info/"
        val BookInfo = "product-info/Books/"
        val NoteBookInfo = "product-info/NoteBooks/"
        val StationariesInfo = "product-info/Stationaries/"
        val DeleteAddress = "info/delete_address/"
        val OrderDetailsUrl = "orders/order-info/"
        val AppVersion = ""
        val UserType = ""
        val IconName = ""
        val empl_id = "empl_id"
        val Version = "version"
        val emplType = "emp_type"
        val department = "department"
        val UserName = "emp_name"
        val UserEmail = "email"
        val islogged = "logged"
        val designation = "designation"
        val branchid = "branchid"
        val logintype = "logintype"
        val MobNumber = "MobNumber"
        val admissionId = "admissionId"
        val ClassType = ""
        val is_logged = false
        val activitycontext: Context? = null
        lateinit var sharedPreferences: Sharedpreferences
        private val httpClient: OkHttpClient? = null
        var schoolpackageactualprice = 0

        // payment gateway ....................................................................................................................
        val ACCESS_CODE = "AVLA18KB43AQ49ALQA"
        val TRANS_URL = "https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction"
       // val TRANS_URL = "https://test.ccavenue.com/transaction/transaction.do?command=initiateTransaction"

        // for loder during fetching server data....................................................................................................
        var mess = ""
        var messSet = false
        var dialog: Dialog? = null
        var step = 0
        var loadingText: TextView? = null
        var timecount: TextView? = null
        var countmsg: TextView? = null
        var handler = Handler()

        fun loadingpopup(
            context: Context?,
            text: String,
            count: String = "",
            check: Boolean = true,
            show: Boolean = true): Dialog {
            dialog = Dialog(context!!, R.style.Theme_Transparent)
            dialog!!.setContentView(R.layout.loadingpopup)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setCancelable(false)
            loadingText = dialog!!.findViewById(R.id.loadingtext)
            timecount = dialog!!.findViewById(R.id.timeCount)
            countmsg = dialog!!.findViewById(R.id.countmsg)

            loadingText!!.setText(text)
            if (check) {
                timecount!!.visibility = View.VISIBLE
                countmsg!!.visibility = View.VISIBLE
                timecount!!.text = count
            } else {
                timecount!!.visibility = View.GONE
                countmsg!!.visibility = View.GONE

            }

            //Repeat(text)
            if (show) {
                dialog!!.show()
            }
            return dialog!!
        }

        fun showDialog(dialog: Dialog?) {
            dialog?.show()
        }

        var runnable = Runnable {
            if (step == 0) {
                loadingText?.setText(mess + ".")
                step = 1
            } else if (step == 1) {
                loadingText?.setText(mess + "..")
                step = 2
            } else {
                loadingText?.setText(mess + "...")
                step = 0
            }
            Repeat(mess)
        }

        fun Repeat(text: String) {
            if (!messSet) {
                mess = text
            } else {
                messSet = false
            }
            handler.postDelayed(runnable, 100)
        }

        fun StopLoader() {
            step = 0
            handler.removeCallbacks(runnable)
            dialog!!.dismiss()
        }

        fun Headers(): Map<String, String> {
            val fields1: MutableMap<String, String> = HashMap()
            if (BuildConfig.DEBUG) {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1.put("Content-Type", "application/json;charset=UTF-8")
            } else {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1.put("Content-Type", "application/json;charset=UTF-8")

            }
            return fields1
        }

        fun HeadersWithAccess(accesstoken: String): Map<String, String> {
            val fields1: MutableMap<String, String> = HashMap()
            if (BuildConfig.DEBUG) {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1["X-ACCESS-TOKEN"] = accesstoken
                fields1.put("Content-Type", "application/json;charset=utf-8")
            } else {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1["X-ACCESS-TOKEN"] = accesstoken
                fields1.put("Content-Type", "application/json;charset=utf-8")
            }
            return fields1
        }

        fun HeadersMultipartWithAccess(accesstoken: String): Map<String, String> {
            val fields1: MutableMap<String, String> = HashMap()
            if (BuildConfig.DEBUG) {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1["X-ACCESS-TOKEN"] = accesstoken

            } else {
                fields1["Accept"] = "application/json"
                fields1["X-API-KEY"] = X_API_KEY
                fields1["X-ACCESS-TOKEN"] = accesstoken

            }
            return fields1
        }


        fun NetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun parseDateToddMMMyyyy(serverdatetime: String?): String? {
            val formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.ENGLISH
            )
            val stringconvertdate = LocalDate.parse(serverdatetime, formatter)
            val inFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
            val date: Date? = inFormat.parse(stringconvertdate.toString())
            val outputDateStr = outputFormat.format(date)
            return outputDateStr
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun dayname(serverdatetime: String?): String {
            val formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.ENGLISH
            )
            val stringconvertdate = LocalDate.parse(serverdatetime, formatter)
            val inFormat = SimpleDateFormat("yyyy-MM-dd")
            val dayname: String =
                SimpleDateFormat("EEEE").format(inFormat.parse(stringconvertdate.toString()))
            return dayname
        }

        fun parseDateStringYYYYMMDD(datee: String?): String? {
            val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            var date: Date? = null
            try {
                date = inputFormat.parse(datee)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return outputFormat.format(date)
        }

        // convert password in md5...................................

        fun MD5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }

        fun Decrypt(base64value: String?): String? {
            val decodedBytes = android.util.Base64.decode(base64value, android.util.Base64.DEFAULT);
            val decodedString = String(decodedBytes)
            return decodedString
        }

        fun loadInstanceServices(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.legalassistanceicon,
                    context.getString(R.string.legalmsg)
                ),
                InstanceServiceModel(
                    R.drawable.noticeissueicon,
                    context.getString(R.string.issumsg)
                ),
                InstanceServiceModel(
                    R.drawable.reviewdocumenticon,
                    context.getString(R.string.revdocmsg)
                ),
                InstanceServiceModel(
                    R.drawable.matrimoniaiconn,
                    context.getString(R.string.matrimsg)
                ),
                InstanceServiceModel(
                    R.drawable.bouncedcheque,
                    context.getString(R.string.cheqmsg)
                ),
                InstanceServiceModel(
                    R.drawable.rentagreementicon,
                    context.getString(R.string.rentmsg)
                ),
                InstanceServiceModel(
                    R.drawable.assistanceicon,
                    context.getString(R.string.assismsg)
                ),
                InstanceServiceModel(
                    R.drawable.marriageicon,
                    context.getString(R.string.marriagemsg)
                ),
                InstanceServiceModel(R.drawable.animalicon, context.getString(R.string.animlmsg)),
                InstanceServiceModel(
                    R.drawable.startuplegalassistanceicon,
                    context.getString(R.string.legalassmsg)
                ),
                InstanceServiceModel(
                    R.drawable.settlechalanicon,
                    context.getString(R.string.settlemsg)
                ),
                InstanceServiceModel(
                    R.drawable.cybercrimeicon,
                    context.getString(R.string.cybermsg)
                ),
                InstanceServiceModel(
                    R.drawable.consumerassistanceicon,
                    context.getString(R.string.conmsg)
                ),

                )
        }

        fun loadInstanceOnlySixServices(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.legalassistanceicon,
                    context.getString(R.string.legalmsg)
                ),
                InstanceServiceModel(
                    R.drawable.noticeissueicon,
                    context.getString(R.string.issumsg)
                ),
                InstanceServiceModel(
                    R.drawable.reviewdocumenticon,
                    context.getString(R.string.revdocmsg)
                ),
                InstanceServiceModel(
                    R.drawable.matrimoniaiconn,
                    context.getString(R.string.matrimsg)
                ),
                InstanceServiceModel(
                    R.drawable.bouncedcheque,
                    context.getString(R.string.cheqmsg)
                ),
                InstanceServiceModel(
                    R.drawable.rentagreementicon,
                    context.getString(R.string.rentmsg)
                ),


                )
        }

        fun loadWhatsIssueServices(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(R.drawable.civilicon, context.getString(R.string.civilmsg)),
                InstanceServiceModel(
                    R.drawable.propertyicon,
                    context.getString(R.string.propertymsg)
                ),
                InstanceServiceModel(
                    R.drawable.corporateicon,
                    context.getString(R.string.corpormsg)
                ),
                InstanceServiceModel(
                    R.drawable.criminalicon,
                    context.getString(R.string.criminalmsg)
                ),
                InstanceServiceModel(
                    R.drawable.divorceicon,
                    context.getString(R.string.divorcemsg)
                ),
                InstanceServiceModel(R.drawable.taxationicon, context.getString(R.string.taxmsg)),
                InstanceServiceModel(
                    R.drawable.bankingicon,
                    context.getString(R.string.chequbouncmsg)
                ),
                InstanceServiceModel(
                    R.drawable.environmenticon,
                    context.getString(R.string.envmsg)
                ),
                InstanceServiceModel(R.drawable.animalicon, context.getString(R.string.animlmsg)),
                InstanceServiceModel(R.drawable.domesticicon, context.getString(R.string.dommsg)),
                InstanceServiceModel(
                    R.drawable.sexualharasmenticon,
                    context.getString(R.string.sexmsg)
                ),
                InstanceServiceModel(
                    R.drawable.motoraccidenticon,
                    context.getString(R.string.motormsg)
                ),

                )
        }

        fun loadWhatsIssueServicesOnlynameList(context: Context): List<InstanceServiceOnlyNameModel> {
            return listOf<InstanceServiceOnlyNameModel>(
                InstanceServiceOnlyNameModel(context.getString(R.string.civilcomm)),
                InstanceServiceOnlyNameModel(context.getString(R.string.corporate)),
                InstanceServiceOnlyNameModel(context.getString(R.string.divorcee)),
                InstanceServiceOnlyNameModel(context.getString(R.string.checkbounc)),
                InstanceServiceOnlyNameModel(context.getString(R.string.animal)),
                InstanceServiceOnlyNameModel(context.getString(R.string.sexualhars)),
                InstanceServiceOnlyNameModel(context.getString(R.string.realest)),
                InstanceServiceOnlyNameModel(context.getString(R.string.criminall)),
                InstanceServiceOnlyNameModel(context.getString(R.string.taxation)),
                InstanceServiceOnlyNameModel(context.getString(R.string.environmentt)),
                InstanceServiceOnlyNameModel(context.getString(R.string.doms)),
                InstanceServiceOnlyNameModel(context.getString(R.string.motoraccid)),
                InstanceServiceOnlyNameModel(context.getString(R.string.child)),
                InstanceServiceOnlyNameModel(context.getString(R.string.arbitration)),
                InstanceServiceOnlyNameModel(context.getString(R.string.labor)),
                InstanceServiceOnlyNameModel(context.getString(R.string.breach)),
                InstanceServiceOnlyNameModel(context.getString(R.string.consumer)),
                InstanceServiceOnlyNameModel(context.getString(R.string.custom)),
                InstanceServiceOnlyNameModel(context.getString(R.string.arms)),
                InstanceServiceOnlyNameModel(context.getString(R.string.immigration)),
                InstanceServiceOnlyNameModel(context.getString(R.string.insurance)),
                InstanceServiceOnlyNameModel(context.getString(R.string.itr)),
                InstanceServiceOnlyNameModel(context.getString(R.string.medical)),
                InstanceServiceOnlyNameModel(context.getString(R.string.trade)),
                InstanceServiceOnlyNameModel(context.getString(R.string.willstrust)),
                InstanceServiceOnlyNameModel(context.getString(R.string.muslimlaws)),
                InstanceServiceOnlyNameModel(context.getString(R.string.humanrights)),
                InstanceServiceOnlyNameModel(context.getString(R.string.humanrights)),
                InstanceServiceOnlyNameModel(context.getString(R.string.media)),
                InstanceServiceOnlyNameModel(context.getString(R.string.mergers)),
                InstanceServiceOnlyNameModel(context.getString(R.string.dataprivacy)),
                InstanceServiceOnlyNameModel(context.getString(R.string.marriagecouns)),
                InstanceServiceOnlyNameModel(context.getString(R.string.employmatter)),
                InstanceServiceOnlyNameModel(context.getString(R.string.techlaws)),
                InstanceServiceOnlyNameModel(context.getString(R.string.citizenship)),
                InstanceServiceOnlyNameModel(context.getString(R.string.outsidemsg)),
                InstanceServiceOnlyNameModel(context.getString(R.string.commercialmsg)),
                InstanceServiceOnlyNameModel(context.getString(R.string.salesmsg))
            )
        }

        fun loadPickcaseExploreOnlynameList(context: Context): List<InstanceServiceOnlyNameModel> {
            return listOf<InstanceServiceOnlyNameModel>(
                InstanceServiceOnlyNameModel(context.getString(R.string.property)),
                InstanceServiceOnlyNameModel(context.getString(R.string.civil)),
                InstanceServiceOnlyNameModel(context.getString(R.string.criminal)),
                InstanceServiceOnlyNameModel(context.getString(R.string.divorce)),
                InstanceServiceOnlyNameModel(context.getString(R.string.environment)),
                InstanceServiceOnlyNameModel(context.getString(R.string.txt)),

                )
        }

        fun loadInstanceExploreServices(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.legalassistanceicon,
                    context.getString(R.string.legaladvocate)
                ),
                InstanceServiceModel(
                    R.drawable.reviewdocumenticon,
                    context.getString(R.string.sendnotice)
                ),
                InstanceServiceModel(
                    R.drawable.reviewdocumenticon,
                    context.getString(R.string.review)
                ),
                InstanceServiceModel(
                    R.drawable.assistanceicon,
                    context.getString(R.string.affidavit)
                ),
                InstanceServiceModel(
                    R.drawable.rentagreementicon,
                    context.getString(R.string.rentmsg)
                ),
                InstanceServiceModel(
                    R.drawable.settlechalanicon,
                    context.getString(R.string.challan)
                ),
                InstanceServiceModel(
                    R.drawable.startuplegalassistanceicon,
                    context.getString(R.string.startup)
                ),
                InstanceServiceModel(
                    R.drawable.marriageicon,
                    context.getString(R.string.marriagemsga)
                ),
            )
        }

        fun treandingSercives(context: Context): MutableList<InstanceServiceModel> {
            return mutableListOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.confidentialicon,
                    context.getString(R.string.confidential)
                ),
                InstanceServiceModel(
                    R.drawable.legalconsulatnticon,
                    context.getString(R.string.legalconsulant)
                ),
                InstanceServiceModel(
                    R.drawable.affordablerateicon,
                    context.getString(R.string.affordablerate)
                ),
                InstanceServiceModel(
                    R.drawable.instantlegaladviceicon,
                    context.getString(R.string.instantadvice)
                )
            )
        }

        fun knowrightsSercives(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.bouncedcheque,
                    context.getString(R.string.knowrighttitlea),context.getString(R.string.knowrighttitleaa)
                ),
                InstanceServiceModel(
                    R.drawable.parentchildrenicon,
                    context.getString(R.string.knowrighttitleb),
                            context.getString(R.string.knowrighttitlebb)
                ),
                InstanceServiceModel(
                    R.drawable.firicon,
                    context.getString(R.string.knowrighttitlec),
                    context.getString(R.string.knowrighttitlecc),
                ),
                InstanceServiceModel(
                    R.drawable.payequalityicon,
                    context.getString(R.string.knowrighttitled),
                    context.getString(R.string.knowrighttitledd)
                ),
                InstanceServiceModel(
                    R.drawable.trafficruleicon,
                    context.getString(R.string.knowrighttitlee),
                    context.getString(R.string.knowrighttitleee)
                ),
                InstanceServiceModel(
                    R.drawable.righttoinformicon,
                    context.getString(R.string.knowrighttitlef),
                    context.getString(R.string.knowrighttitleff)
                )

                )
        }

        fun trendingadvocate(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(
                    R.drawable.manojkumar,
                    "Senior Advocate Manoj Kumar",
                    "Experience: 14 years",
                    "Delhi High Court, Patiala House court, Delhi"
                ),
                InstanceServiceModel(
                    R.drawable.dineshkumar,
                    "Advocate Dinesh Yadav",
                    "Experience: 8 years",
                    "Delhi High Court, Gurugram District Court"
                ),
                InstanceServiceModel(
                    R.drawable.sakshi,
                    "Advocate Sakshi Goyal",
                    "Experience: 4 years",
                    "Karkardooma District Court"
                ),
                InstanceServiceModel(
                    R.drawable.pranav,
                    "Advocate Pranav Bhaskar",
                    "Experience: 4 years",
                    "Dwarka District Court"
                ),
            )
        }

       /* fun feedlist(context: Context): List<InstanceServiceModel> {
            return listOf<InstanceServiceModel>(
                InstanceServiceModel(R.drawable.bannerhomea, context.getString(R.string.feedlist)),
                InstanceServiceModel(R.drawable.bannerhomeb, context.getString(R.string.feedlist)),
                InstanceServiceModel(R.drawable.bannerhomea, context.getString(R.string.feedlist)),
                InstanceServiceModel(R.drawable.bannerhomeb, context.getString(R.string.feedlist)),
            )
        }*/

        fun incrementDateByOne(date: Date): Date {
            var calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, 1)
            var nextdate = calendar.time
            return nextdate
        }

        fun decrementDateByOne(date: Date): Date {
            var calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, -1)
            var previousDate = calendar.time
            return previousDate
        }

    }


}