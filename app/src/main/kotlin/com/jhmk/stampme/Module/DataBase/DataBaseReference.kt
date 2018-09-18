package com.jhmk.stampme.Module.DataBase

import com.google.firebase.database.FirebaseDatabase

object DataBaseReference {
    val mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
}