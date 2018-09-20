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
    val mView = view

    override fun requestLogin(user: User) {
        Log.d(TAG, "##### reuqestLogin ##### id : ${user.userId} pw : ${user.userPw}")
        DataBaseReference.mUsersDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### reuqestLogin ##### onCancelled")
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                mView.onResultLogin(false, R.string.toast_login_failed, user)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### reuqestLogin ##### onDataChange")
                var child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key == user.userId) {
                        val s = dataSnapShot.child(user.userId).child("userPw").value
                        if (dataSnapShot.child(user.userId).child("userPw").value == user.userPw) {
                            mView.onResultLogin(true, R.string.toast_login_success, user)
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
        Log.d(TAG, "##### requestRegister ##### id : ${user.userId} pw : ${user.userPw}")
        DataBaseReference.mUsersDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.d(TAG, "##### requestRegister ##### onCancelled")
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                mView.onResultRegister(false, R.string.toast_register_failed)
            }

            override fun onDataChange(dataSnapShot: DataSnapshot) {
                Log.d(TAG, "##### requestRegister ##### onDataChange")
                var child = dataSnapShot.children.iterator()

                while (child.hasNext()) {
                    if (child.next().key.equals(user.userId)) {
                        mView.onResultRegister(false, R.string.toast_register_failed)
                        DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
                        return
                    }
                }
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userPw").setValue(user.userPw)
                DataBaseReference.mUsersDatabaseReference.child(user.userId).child("userStamp").setValue(0)
                mView.onResultRegister(false, R.string.toast_register_success)
                DataBaseReference.mUsersDatabaseReference.removeEventListener(this)
            }
        })
    }

    override fun saveUserId(context: Context, id: String) {
        Log.d(TAG, "##### saveUserId ##### id : $id")
        PreferencesManager.saveUserId(context, id)
    }

    override fun saveUserPassword(context: Context, pw: String) {
        Log.d(TAG, "##### saveUserPassword ##### id : $pw")
        PreferencesManager.saveUserPw(context, pw)
    }
}