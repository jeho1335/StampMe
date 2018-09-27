package com.jhmk.stampme.Module.Register

import android.util.Log
import android.view.View
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
        if(id.contentEquals(".")){
            mView.onResultNextStep(false, R.string.toast_register_failed_include_comma)
            return
        }
        if (id == "" || pw == "" || confirmPw == "") {
            mView.onResultNextStep(false, R.string.toast_register_failed)
            return
        }
        if (pw == confirmPw) {
            mView.onResultNextStep(true, R.string.toast_register_success_require_detail_info)
        } else {
            mView.onResultNextStep(false, R.string.toast_register_failed_not_compare_pw)
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

    override fun requestBackButton(view: View) {
        Log.d(TAG, "##### requestBackButton ##### ${view.visibility}")
        if(view.visibility == View.VISIBLE){
            EventBus.getDefault().post(EventBusObject(ConstVariables.EVENTBUS_SHOW_LOGIN))
        }else{
            view.visibility = View.VISIBLE
        }
    }
}