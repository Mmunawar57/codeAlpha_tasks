package com.codealpha.app.collegealert.appclass

import android.app.Application
import com.codealpha.app.collegealert.utils.SharedPref
import com.google.firebase.FirebaseApp

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        FirebaseApp.initializeApp(this)
    }
}