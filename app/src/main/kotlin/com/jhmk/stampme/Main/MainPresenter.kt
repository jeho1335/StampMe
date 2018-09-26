package com.jhmk.stampme.Main

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jhmk.stampme.Model.User
import com.jhmk.stampme.Module.DataBase.DataBaseReference
import com.jhmk.stampme.Module.DataBase.PreferencesManager
import com.jhmk.stampme.R

class MainPresenter(view: Main.view) : Main.presenter {
    val TAG = this.javaClass.simpleName
    private val mView = view

    override fun requestLogin(user: User) {
        DataBaseReference.mUsersDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### reuqestLogin ##### onCancelled")
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                mView.onResultLogin(false, R.string.toast_login_failed, user)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### reuqestLogin ##### onDataChange")
                val child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key == user.userId) {
                        val s = dataSnapShot.child(user.userId).child("userPw").value
                        if (dataSnapShot.child(user.userId).child("userPw").value == user.userPw) {
                            val currentUser = dataSnapShot.child(user.userId).getValue(User().javaClass)

                            if(currentUser != null) {
                                mView.onResultLogin(true, R.string.toast_login_success, currentUser)
                            }else{
                                mView.onResultLogin(false, R.string.toast_login_failed, user)
                            }
                            DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                            return
                        }
                    }
                }
                mView.onResultLogin(false, R.string.toast_login_failed, user)
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
            }
        })
    }

    override fun requestRegister(user: User) {
        DataBaseReference.mUsersDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### requestRegister ##### onCancelled")
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                mView.onResultRegister(false, R.string.toast_register_duplicate_failed, user)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### requestRegister ##### onDataChange")
                val child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key.equals(user.userId)) {
                        mView.onResultRegister(false, R.string.toast_register_duplicate_failed, user)
                        DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                        return
                    }
                }
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userId").setValue(user.userId)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userPw").setValue(user.userPw)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userName").setValue(user.userName)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userPhoneNumber").setValue(user.userPhoneNumber)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userLocation").setValue(user.userLocation)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userType").setValue(user.userType)
                mView.onResultRegister(true, R.string.toast_register_success, user)
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
            }
        })
    }

    override fun requestSaveUserInfo(context: Context, user: User) {
        Log.d(TAG, "##### requestSaveUserInfo ##### id : ${user.userId}")
        PreferencesManager.saveUserInfo(context, user)
    }

    override fun requestDeleteUserInfo(context: Context, user: User) {
        Log.d(TAG, "##### requestDeleteUserInfo ##### id : ${user.userId}")
        PreferencesManager.deleteUserInfo(context, user)
        mView.onResultLogout(PreferencesManager.deleteUserInfo(context, user), R.string.string_logout)
    }
}