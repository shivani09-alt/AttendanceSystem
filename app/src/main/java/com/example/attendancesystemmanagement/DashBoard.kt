package com.example.attendancesystemmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class DashBoard : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_dash_board, container, false)

        val addStudent:MaterialCardView =view.findViewById(R.id.add_student)
        val addStaff:MaterialCardView =view.findViewById(R.id.add_faculty)
        val logout:MaterialCardView=view.findViewById(R.id.logout)
        val viewStudentList:MaterialCardView=view.findViewById(R.id.view_student)
        val viewStaffList:MaterialCardView=view.findViewById(R.id.view_faculty)
        viewStaffList.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "staff")
            findNavController().navigate(R.id.dashBoard_to_view_user_list, bundle)
        }
        viewStudentList.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "student")
            findNavController().navigate(R.id.dashBoard_to_view_user_list, bundle)
        }

        val mAuth:FirebaseAuth= FirebaseAuth.getInstance()
        logout.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.dashBoard_to_loginPage)
        }
        addStudent.setOnClickListener {
            findNavController().navigate(R.id.addStudentDetails)
        }
        addStaff.setOnClickListener {
            findNavController().navigate(R.id.addStaff)
        }
        return  view
    }

}