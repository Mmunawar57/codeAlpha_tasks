package com.codealpha.app.collegealert.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.codealpha.app.collegealert.R
import com.codealpha.app.collegealert.utils.SharedPref

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateNavGraphFlow()
    }
    private fun updateNavGraphFlow(){
        val navHostFragment=supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        val navController=navHostFragment.navController
        val navGraph=navController.navInflater.inflate(R.navigation.nav_graph)
        if(SharedPref.getString("user")=="IsStudent") {
            if(SharedPref.getBoolean("std_login")) {
                navGraph.setStartDestination(R.id.homeFragment)
            } else {
                navGraph.setStartDestination(R.id.dashboardFragment)
            }
        }else if(SharedPref.getString("user")=="IsAdmin"){
            if(SharedPref.getBoolean("admin_login")) {
                navGraph.setStartDestination(R.id.adminHomeFragment)
            } else {
                navGraph.setStartDestination(R.id.dashboardFragment)
            }
        }
        navController.graph=navGraph
    }
    override fun onBackPressed() {
        val fragmentContainer= Navigation.findNavController(this, androidx.navigation.fragment.R.id.nav_host_fragment_container)
        when(fragmentContainer.currentDestination!!.id) {
            R.id.newfeedsFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.detailsFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.homeFragment -> {
                finishAffinity()
            }

            R.id.dashboardFragment -> {
                finishAffinity()
            }

            R.id.uploadNewsFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.settingFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.calenderFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.studentLoginFragment -> {
                finishAffinity()
            }

            R.id.studentSignUpFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.adminLoginFragment -> {
                finishAffinity()
            }

            R.id.adminRegisterFragment -> {
                fragmentContainer.popBackStack()
            }

            R.id.adminHomeFragment -> {
                finishAffinity()
            }
            R.id.deleteNewsFragment->{
                fragmentContainer.popBackStack()
            }
            R.id.uploadNewsFragment->{
                fragmentContainer.popBackStack()
            }
        }
    }
}