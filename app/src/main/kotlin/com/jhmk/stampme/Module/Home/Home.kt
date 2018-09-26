package com.jhmk.stampme.Module.Home

import android.content.Context
import com.jhmk.stampme.Model.Shops

interface Home {
    interface view {
        fun onResultAroundShopList(list: MutableList<Shops?>)
    }

    interface presenter {
        fun requestAroundShopList(context: Context)
        fun requestChangeToolbar(string : String)
    }
}