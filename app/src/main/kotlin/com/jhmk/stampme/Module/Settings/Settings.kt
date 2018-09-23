package com.jhmk.stampme.Module.Settings

import com.jhmk.stampme.Model.User

interface Settings {
    interface presenter{
        fun requestLogout(user : User)
    }
}