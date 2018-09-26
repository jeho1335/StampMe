package com.jhmk.stampme.Model

import java.io.Serializable

open class User : Serializable {
    var userId = ""
    var userPw = ""
    var userName = ""
    var userPhoneNumber = ""
    var userLocation = ""
    var userType = -1

    constructor()

    constructor(userId: String, userPw: String) {
        this.userId = userId
        this.userPw = userPw
    }

    constructor(userId: String, userPW: String, userName: String, userEmail: String, userLocation: String, userType: Int) {
        this.userId = userId
        this.userPw = userPW
        this.userName = userName
        this.userPhoneNumber = userEmail
        this.userLocation = userLocation
        this.userType = userType
    }

}