package com.jhmk.stampme.Module.Login

import android.util.Log
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.Utils.ConstVariables
import com.jhmk.stampme.Module.Utils.EventBusObject
import org.greenrobot.eventbus.EventBus

class LoginPresenter : Login.presenter {
    val TAG = this.javaClass.simpleName

    override fun requestLogin(user : User) {
        Log.d(TAG, "##### requestLogin ##### id : ${user.userId} pw : ${user.userPw}")
        EventBus.getDefault().post(EventBusObject(ConstVariables.EVENTBUS_REQUELST_LOGIN,  user as Any))
    }

    override fun requestRegister(user : User) {
        Log.d(TAG, "##### requestRegister ##### id : ${user.userId} pw : #pw")
        EventBus.getDefault().post(EventBusObject(ConstVariables.EVENTBUS_REQUELST_REGISTER, user as Any))
    }
}