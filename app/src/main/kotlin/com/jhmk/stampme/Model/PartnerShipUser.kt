package com.jhmk.stampme.Model

class PartnerShipUser(userId: String, userPW: String, userName: String, userEmail: String, userType: Int, val userAffilication: Int) : User(userId, userPW, userName, userEmail, userType) {
    val TAG = this.javaClass.simpleName

}