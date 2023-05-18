package com.appsnipp.eadvokate.utils
import android.content.Context
import android.content.SharedPreferences
import com.appsnipp.eadvokate.model.BannerResponse
import com.appsnipp.eadvokate.model.ServicesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

open class Sharedpreferences{

    var editor:SharedPreferences.Editor?=null
    val CHECK_LOGIN:String="check_login"

    companion object{
        var  mRef:Sharedpreferences?=null
        var prefs: SharedPreferences? = null

        @JvmStatic
        fun getInstance(context: Context):Sharedpreferences{
            if(mRef==null)
            {
                mRef= Sharedpreferences()
                prefs=context.getSharedPreferences("MyPref", 0)
                return mRef!!
            }
            return mRef!!
        }
    }

    fun setEmail(key:String,value:String) {
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getEmail(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }

    fun setMobNumber(key:String,value:String) {
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()

    }
    fun getMobNumber(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }

    fun islogged(key:String,value:Boolean) {
        editor= prefs?.edit()
        editor!!.putBoolean(key,value)
        editor!!.commit()
    }
    fun getlogged(key:String):Boolean
    {
        val  lvalue:Boolean
        lvalue= prefs!!.getBoolean(key,false)!!
        return lvalue
    }
    fun setUserInfo(key:String,value:String) {
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }
    fun getUserInfo(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setAccessToken(key:String,value:String) {
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }
    fun getAccessToken(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }

    fun setData(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getData(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }

    fun setstoptime(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getstoptime(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setuserId(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getuserId(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setCounter(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getCounter(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setUserName(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getUserName(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setUsermob(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getUsermob(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }
    fun setUsertype(key:String, value:String){
        editor= prefs?.edit()
        editor!!.putString(key,value)
        editor!!.commit()
    }

    fun getUsertype(key:String):String
    {
        val  lvalue:String
        lvalue= prefs!!.getString(key,"")!!
        return lvalue
    }

    fun setUserProfileImage(value:String){
        editor= prefs?.edit()
        editor!!.putString("UserImage",value)
        editor!!.commit()
    }

    fun getUserprofile():String{
        val  userimage:String
        userimage= prefs!!.getString("UserImage","")!!
        return userimage
    }

    open fun setlanguage(key: String?, language: String?) {
        editor = prefs?.edit()
        editor!!.putString(key, language)
        editor!!.commit()
    }

    open fun getlanguage(key: String?): String? {
        return prefs?.getString(key, "")
    }

    open fun saveServicesData(list: MutableList<ServicesResponse>?) {
        val gson = Gson()
        // getting data from gson and storing it in a string.
        val json = gson.toJson(list)
        editor!!.putString("service", json)
        editor!!.apply()
    }

    open fun getServicesData(): MutableList<ServicesResponse>  {
        val gson = Gson()
        val json: String = prefs!!.getString("service", null)!!

        // below line is to get the type of our array list.
        val type: Type = object : TypeToken<MutableList<ServicesResponse>>() {}.getType()

        // in below line we are getting data from gson
        // and saving it to our array list
        var courseModalArrayList: ArrayList<ServicesResponse> = gson.fromJson<ArrayList<ServicesResponse> >(json, type)

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = ArrayList<ServicesResponse> ()
        }
        return courseModalArrayList
    }


    open fun saveDashboardData(list: MutableList<BannerResponse>?) {
        val gson = Gson()
        // getting data from gson and storing it in a string.
        val json = gson.toJson(list)
        editor!!.putString("banner", json)
        editor!!.apply()
    }

    open fun getDashboardData(): MutableList<BannerResponse>  {
        val gson = Gson()
        val json: String = prefs!!.getString("banner", null)!!

        // below line is to get the type of our array list.
        val type: Type = object : TypeToken<MutableList<BannerResponse>>() {}.getType()

        // in below line we are getting data from gson
        // and saving it to our array list
        var courseModalArrayList: ArrayList<BannerResponse> = gson.fromJson<ArrayList<BannerResponse> >(json, type)

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = ArrayList<BannerResponse> ()
        }
        return courseModalArrayList
    }

    open fun savePosition(key:String,position:Int){
        editor = prefs?.edit()
        editor!!.putInt(key, position)
        editor!!.commit()
    }

    open fun getPosition(key:String): Int? {
        return prefs?.getInt(key, -1)
    }


}


