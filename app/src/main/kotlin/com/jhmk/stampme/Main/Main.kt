package com.jhmk.stampme.Main

interface Main {
    interface view{
        fun onResultLogin(result : Boolean, msg : String)
        fun onResultRegister(result : Boolean, msg : String)
    }
    interface presenter{
        fun requestLogin(id : String, pw : String)
        fun requestRegister(id : String, pw : String)
    }
}