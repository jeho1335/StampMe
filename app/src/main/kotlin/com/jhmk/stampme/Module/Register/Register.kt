package com.jhmk.stampme.Module.Register

import com.jhmk.stampme.Model.User

interface Register {
    interface view{
        fun onResultNextStep(flag : Boolean)
        fun onResultCreateAccount(flag : Boolean, msg : Int)
    }
    interface presenter{
        fun requestNextStep(id : String, pw : String, confirmPw : String)
        fun requestCreateAccount(fullname : String, phoneNumber : String, location : String)
        fun requestFinishRegister(user : User)
        fun requestChangeToolbar(string : String)
    }
}