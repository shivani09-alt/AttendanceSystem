package com.example.attendancesystemmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class ViewUserData : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_view_user_data, container, false)
        val id:String = arguments?.getString("id").toString()

        val dbHandler = DBHandler(activity as AppCompatActivity)
        var list:ArrayList<Users> = dbHandler.getUserList(id)
        val nameText: TextInputEditText =view.findViewById(R.id.name)
        val phoneNumber: TextInputEditText =view.findViewById(R.id.phoneNumber)
        val email: TextInputEditText =view.findViewById(R.id.email)
        val cancel: Button =view.findViewById(R.id.cancel)
        val selectedDept: TextInputEditText =view.findViewById(R.id.selecteddept)
        val selectyear: TextInputEditText =view.findViewById(R.id.selectedYear)
        if(list.size>0) {
            nameText.setText(list.get(0).name)
            phoneNumber.setText(list.get(0).phone)
            email.setText(list.get(0).email)
            selectedDept.setText(list.get(0).department)
            selectyear.setText(list.get(0).year)
        }
        cancel.setOnClickListener{
            (activity as AppCompatActivity).onBackPressed()
        }


        return view
    }

}