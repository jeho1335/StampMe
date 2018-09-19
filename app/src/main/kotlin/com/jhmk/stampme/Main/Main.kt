package com.jhmk.stampme.Main

import android.content.Context
import com.jhmk.stampme.Model.User

interface Main {
    interface view{
        fun onResultLogin(result : Boolean, msg : Int, user : User)
        fun onResultRegister(result : Boolean, msg : Int)
    }
    interface presenter{
        fun requestLogin(user : User)
        fun requestRegister(user : User)
        fun saveUserId(context : Context, id : String)
        fun saveUserPassword(context : Context, pw : String)
    }
}