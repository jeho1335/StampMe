package com.jhmk.stampme.Model

import java.io.Serializable

class MyStamps {
    var stampsSource = ""
    var stampSourceType = ""
    var stampSourceImageUrl = ""
    var stampReason = ""
    var stampAddress = ""

    constructor(stampSource: String, stampSourceType: String, stampSourceImageUrl: String, stampReason: String, stampAddress: String) {
        this.stampsSource = stampSource
        this.stampSourceType = stampSourceType
        this.stampSourceImageUrl = stampSourceImageUrl
        this.stampReason = stampReason
        this.stampAddress = stampAddress
    }
}