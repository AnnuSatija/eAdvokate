package com.appsnipp.eadvokate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsnipp.eadvokate.R

class ChooseLawyerUsingDocumentUploadFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_choose_lawyer_using_document_upload, container, false)
         init(view)
         return view
     }

          fun init(view:View){

     }

}