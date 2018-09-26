package com.jhmk.stampme.Module.Register

import android.util.Log
import com.jhmk.stampme.Model.ConstVariables
import com.jhmk.stampme.Model.EventBusObject
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.R
import org.greenrobot.eventbus.EventBus

class RegisterPresenter(view: Register.view) : Register.presenter {
    val TAG = this.javaClass.simpleName
    val mView = view

    override fun requestNextStep(id: String, pw: String, confirmPw: String) {
        Log.d(TAG, "##### requestNextStep #####")
        if (id == "" || pw == "" || confirmPw == "") {
            mView.onResultNextStep(false)
            return
        }
        if (pw == confirmPw) {
            mView.onResultNextStep(true)
        } else {
            mView.onResultNextStep(false)
        }
        return
    }

    override fun requestCreateAccount(fullname: String, phoneNumber: String, location: String) {
        Log.d(TAG, "##### requestCreateAccount #####")
        if (fullname == "" || phoneNumber == "" || location == "") {
            mView.onResultCreateAccount(false, R.string.toast_register_failed)
            return
        } else {
            mView.onResultCreateAccount(true, R.string.toast_register_success)
        }
    }

    override fun requestFinishRegister(user : User) {
        Log.d(TAG, "##### requestFinishRegister #####")
        EventBus.getDefault().post(EventBusObject(ConstVariables.EVENTBUS_REQUELST_REGISTER, user))
    }

    override fun requestChangeToolbar(string: String) {
        Log.d(TAG, "##### requestChangeToolbar #####")
        EventBus.getDefault().post(EventBusObject(ConstVariables.EVENTBUS_CHANGE_TOOLBAR, string))
    }
}