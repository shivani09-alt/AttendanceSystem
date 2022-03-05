package com.example.attendancesystemmanagement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginWithOtp : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var dbHandler:DBHandler
    var check:Boolean=false


    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login_with_otp, container, false)
        auth=FirebaseAuth.getInstance()
        val Login=view.findViewById<Button>(R.id.loginBtn)
        val mobileNumber=view.findViewById<EditText>(R.id.phoneNumber)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(activity, "ONVerificationCompleted", Toast.LENGTH_LONG).show()

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("failed", e.toString())
                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
                var type =""
                if(check==true){
                    type="AlreadyExist"
                }
                else{
                    type="NotExist"
                }
                val bundle = bundleOf("storedVerificationId" to storedVerificationId,"type" to type)

                findNavController().navigate(
                    R.id.otpVerification,bundle
                )

            }
        }

        Login.setOnClickListener{
            var number=mobileNumber.text.toString().trim()
            if(!number.isEmpty()){
                number="+91"+number
                Log.d("mobileNumber",number)
                dbHandler= DBHandler(activity as AppCompatActivity)
                val list:ArrayList<Users> = dbHandler.getDetails()
                for(i in 0..list.size-1) {
                    val num = list.get(0).phone
                    Log.d("numBerVal", num)
                    if(number.equals(num)){
                        check=true
                    }
                }
                if(check==true){
                    sendVerificationcode (number)

                }
                else{
                    sendVerificationcode (number)

                }
            }
            else{
                Toast.makeText(activity,"Enter mobile number", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    private fun sendVerificationcode(number: String) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(it) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }

}