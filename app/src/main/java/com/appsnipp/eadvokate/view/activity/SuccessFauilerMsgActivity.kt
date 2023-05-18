package com.appsnipp.eadvokate.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.appsnipp.eadvokate.activity.MainActivity
import com.appsnipp.eadvokate.databinding.ActivitySuccessFauilerMsgBinding

class SuccessFauilerMsgActivity : AppCompatActivity() {
       lateinit var transstatus:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySuccessFauilerMsgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("transStatus")){
            transstatus=intent.getStringExtra("transStatus") as String
        }
         if(transstatus.equals("Your transaction is successful.")){
             binding.successcard.visibility= View.VISIBLE
             binding.failurecard.visibility=View.GONE
         }else if(transstatus.equals("Fail")) {
             binding.successcard.visibility= View.GONE
             binding.failurecard.visibility=View.VISIBLE
         }
        else{
             binding.successcard.visibility= View.GONE
             binding.failurecard.visibility=View.VISIBLE
         }

        binding.contibutton.setOnClickListener {
            finishAffinity()
            val intent= Intent(this@SuccessFauilerMsgActivity, MainActivity::class.java)
            startActivity(intent)
        }

      }

}