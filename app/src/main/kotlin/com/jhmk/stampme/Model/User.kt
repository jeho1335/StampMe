package com.jhmk.stampme.Model

import java.io.Serializable

open class User : Serializable {
    open var userId = ""
    open var userPw = ""
    open var userName = ""
    open var userEmail = ""
    open var userType = -1

    constructor(userId: String, userPw: String) {
        this.userId = userId
        this.userPw = userPw
    }

    constructor(userId: String, userPW: String, userName: String, userEmail: String, userType : Int) {
        this.userId = userId
        this.userPw = userPW
        this.userName = userName
        this.userEmail = userEmail
        this.userType = userType
    }

}