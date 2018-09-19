package com.jhmk.stampme.Model

class GeneralUser(userId: String, userPW: String, userName: String, userEmail: String, userType : Int, val userStampNumber: Int) : User(userId, userPW, userName, userEmail, userType){
    val TAG = this.javaClass.simpleName

}