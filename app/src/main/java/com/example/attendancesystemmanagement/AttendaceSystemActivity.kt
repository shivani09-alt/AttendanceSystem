package com.example.attendancesystemmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

class AttendaceSystemActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var dbHandler:DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendace_system)
        auth=FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.layout_navigation)
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.dashBoard) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
        if(currentUser != null) {
            Log.d("currentUserNumber", currentUser.phoneNumber.toString())
            dbHandler= DBHandler(this)
            val list:ArrayList<Users> = dbHandler.getDetails()
            var check =false
            for(i in 0..list.size-1) {
                val num = list.get(0).phone
                Log.d("numBerVal", num)
                if(currentUser.phoneNumber.equals(num)){
                    check=true
                }
            }
            if(check==true){
                navGraph.startDestination=R.id.dashBoard

            }
            else{
                navGraph.startDestination=R.id.addDetails

            }
        }
        else{
            navGraph.startDestination=R.id.loginWithOtp
        }
        navHostFragment.navController.graph = navGraph


    }
}

