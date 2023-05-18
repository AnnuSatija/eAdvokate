package com.appsnipp.nagarjunabookstore.utils

import android.content.Context
import android.content.Intent
import java.io.File

interface OnIntentReceived {
    fun onIntentPayment(context: Context?, i: Intent?, resultCode: Int, requestCode: Int)
}