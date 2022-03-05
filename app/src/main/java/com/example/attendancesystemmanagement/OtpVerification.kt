package com.example.attendancesystemmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class OtpVerification : Fragment() {
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view:View
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_otp_verification, container, false)
        auth=FirebaseAuth.getInstance()

        val storedVerificationId=arguments?.getString("storedVerificationId")
        var type =arguments?.getString("type")

        val verify=view.findViewById<Button>(R.id.verifyBtn)
        val otpGiven=view.findViewById<EditText>(R.id.id_otp)
        verify.setOnClickListener{
            val otp=otpGiven.text.toString().trim()
            if(!otp.isEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(activity,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

        return view;
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        activity?.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {

                        findNavController().navigate(R.id.otpVerification_to_addDetails)
                        // ...
                    } else {
    // Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
    // The verification code entered was invalid
                            Toast.makeText(activity,"Invalid OTP",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}