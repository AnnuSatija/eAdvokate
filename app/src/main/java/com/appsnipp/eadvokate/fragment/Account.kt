package com.appsnipp.eadvokate.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.appsnipp.eadvokate.R
import com.appsnipp.eadvokate.activity.*
import com.appsnipp.eadvokate.databinding.FragmentAccountBinding
import com.appsnipp.eadvokate.multimedia.OpenMedia
import com.appsnipp.eadvokate.utils.Sharedpreferences
import com.appsnipp.eadvokate.view.activity.MyCases
import com.appsnipp.eadvokate.view.activity.MyConsultationActivity
import com.appsnipp.eadvokate.view.activity.PaymentRefundActivity


class Account : Fragment() {

      lateinit var sharedpreferences:Sharedpreferences
      private var binding: FragmentAccountBinding? = null
    // the non null value of the _binding
      private val _binding get() = binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=FragmentAccountBinding.inflate(layoutInflater,container,false)
        sharedpreferences= Sharedpreferences.getInstance(requireActivity())
        setclicklistner()
        return binding!!.root

    }

    fun setclicklistner(){
        binding!!.helpcentre.visibility=View.GONE

        binding!!.shareapp.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setChooserTitle("Eadvokate")
                .setText("http://play.google.com/store/apps/details?id=" + requireActivity().getPackageName())
                .startChooser();
        }

        binding!!.rateus.setOnClickListener {

            val uri: Uri = Uri.parse("market://details?id="+requireActivity().getPackageName())
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="+requireActivity().getPackageName())))
            }
        }

        binding!!.myprofilecard.setOnClickListener {
            var intent=Intent(requireContext(),AdvocateProfileActivity::class.java)
            startActivity(intent)
        }

        binding!!.appointmentscard.setOnClickListener {
            var intent=Intent(requireContext(),MyAppointmentsActivity::class.java)
            startActivity(intent)
        }

        binding!!.myadvocatecard.setOnClickListener {
            var intent=Intent(requireContext(),MyAdvocatesACtivity::class.java)
            startActivity(intent)
        }

        binding!!.paymentrefundcard.setOnClickListener {
            var intent=Intent(requireContext(), PaymentRefundActivity::class.java)
            startActivity(intent)
        }

        binding!!.mycases.setOnClickListener {
            var intent=Intent(requireContext(), MyCases::class.java)
            startActivity(intent)
        }

        binding!!.helpcentre.setOnClickListener {
            var intent=Intent(requireContext(),HelpCenterActivity::class.java)
            startActivity(intent)
        }

        binding!!.privacycard.setOnClickListener {
            val url = "https://eadvokate.com/privacy-policies"
            val i =  Intent(context,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Privacy Policy")
            i.putExtra("account","account")
            startActivity(i)
        }

        binding!!.termcondition.setOnClickListener {
            val url = "https://eadvokate.com/terms-and-conditions";
            val i =  Intent(context,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Terms and Conditions")
            i.putExtra("account","account")
            startActivity(i)
        }

        binding!!.refundpolicycard.setOnClickListener {
            val url = "https://eadvokate.com/cancellation-policy";
            val i =  Intent(context,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","Refund Policy")
            i.putExtra("account","account")
            startActivity(i)
        }

        binding!!.aboutuscard.setOnClickListener {
            val url = "https://eadvokate.com/about-us";
            val i =  Intent(context,OpenMedia::class.java)
            i.putExtra("URL",url)
            i.putExtra("Title","About Us")
            i.putExtra("account","account")
            startActivity(i)
        }

        binding!!.myconsulationcard.setOnClickListener {
            var intent=Intent(requireContext(), MyConsultationActivity::class.java)
            startActivity(intent)
        }

        binding!!.contactctus.setOnClickListener {
            val builder= AlertDialog.Builder(requireContext(),R.style.Theme_Transparent1).create()
            val view = layoutInflater.inflate(R.layout.contactuslayout,null)
            builder.setCanceledOnTouchOutside(true)
            var window=builder.window
            window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)

            var contactuslayout= view.findViewById<RelativeLayout>(R.id.contactuslayout)
            var cancelicon= view.findViewById<ImageView>(R.id.cancelicon)
            var calllayout= view.findViewById<LinearLayout>(R.id.calllayout)
            var emaillayout= view.findViewById<LinearLayout>(R.id.emailId)
            var whatsapp= view.findViewById<LinearLayout>(R.id.whatsapp)


            cancelicon?.setOnClickListener {
                builder.dismiss()
            }

            calllayout.setOnClickListener {
               var call =  Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel: 8510000497"))
                startActivity(call)
            }

            emaillayout.setOnClickListener {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:contact@eadvokate.com")
                }
                startActivity(Intent.createChooser(emailIntent, "Send feedback"))
            }

            whatsapp.setOnClickListener {
                val contact = "+91 8510000497" // use country code with your phone number
                val url = "https://api.whatsapp.com/send?phone=$contact"
                try {
                    val pm = requireContext()!!.packageManager
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: PackageManager.NameNotFoundException) {
                   // Toast.makeText(requireContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }

            }

            builder. setView(view)
            builder.show()
        }


        binding!!.logoutcard.setOnClickListener {
            val builder= AlertDialog.Builder(requireContext(),R.style.Theme_Transparent1).create()
            val view = layoutInflater.inflate(R.layout.logoutalert,null)
            builder.setCanceledOnTouchOutside(false)
            var window=builder.window
            window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setGravity(Gravity.CENTER)

            var cancel=view.findViewById<TextView>(R.id.cancel)
            var logout=view.findViewById<TextView>(R.id.logout)

            cancel.setOnClickListener {
                builder.dismiss()
            }

            logout.setOnClickListener {
                if(sharedpreferences.getlogged("Userlogin")){
                    sharedpreferences.islogged("Userlogin",false)
                    var intent=Intent(requireContext(),SignInActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }

            builder. setView(view)
            builder.show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}