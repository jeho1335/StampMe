package com.jhmk.stampme.Module.Register

import android.view.View
import com.jhmk.stampme.Model.User

interface Register {
    interface view{
        fun onResultNextStep(flag : Boolean, msg : Int)
        fun onResultCreateAccount(flag : Boolean, msg : Int)
    }
    interface presenter{
        fun requestNextStep(id : String, pw : String, confirmPw : String)
        fun requestCreateAccount(fullname : String, phoneNumber : String, location : String)
        fun requestFinishRegister(user : User)
        fun requestBackButton(view : View)
    }
}