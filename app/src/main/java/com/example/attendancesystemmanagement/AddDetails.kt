package com.example.attendancesystemmanagement

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class AddDetails : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var departmentList = arrayListOf<String>()//Creating an empty arraylist
//    var departmentList: List<String>? = listOf()
    private var departmentVal:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth=FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_add_details, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Add Details"
        val nameText:TextInputEditText=view.findViewById(R.id.name)
        val phoneNumber:TextInputEditText=view.findViewById(R.id.phoneNumber)
        val email:TextInputEditText=view.findViewById(R.id.email)
        val location:TextInputEditText=view.findViewById(R.id.location)
        val organnization:TextInputEditText=view.findViewById(R.id.organizationName)
        val department: AppCompatEditText =view.findViewById(R.id.department)
        val saveButton:ImageView=view.findViewById(R.id.saveButton)
        val chip:ChipGroup=view.findViewById(R.id.chipGroup)
        val registerBtn:Button=view.findViewById(R.id.registerBtn)
        saveButton.setOnClickListener {
            if (department.text.toString().isEmpty()) {
                department.setBackgroundResource(R.drawable.error_box)

            } else {
                departmentList.add(department.text.toString())
                chip.addChip(activity as AppCompatActivity, department.text.toString())
                department.setText("")
            }
        }
        phoneNumber.setText(currentUser?.phoneNumber.toString())
        nameText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameText.setBackgroundResource(R.drawable.box_stroke)
                if(p0.toString().isNotEmpty()) {
                    nameText.setBackgroundResource(R.drawable.box_stroke)
                }
                else{
                    nameText.setBackgroundResource(R.drawable.error_box)

                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        department.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()) {
                    department.setBackgroundResource(R.drawable.box_stroke)
                }





            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        email.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()) {
                    email.setBackgroundResource(R.drawable.box_stroke)
                }
                else{
                    email.setBackgroundResource(R.drawable.error_box)

                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        location.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()) {
                    location.setBackgroundResource(R.drawable.box_stroke)
                }
                else{
                    location.setBackgroundResource(R.drawable.error_box)

                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        organnization.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                organnization.setBackgroundResource(R.drawable.box_stroke)
                if(p0.toString().isNotEmpty()) {
                    organnization.setBackgroundResource(R.drawable.box_stroke)
                }
                else{
                    organnization.setBackgroundResource(R.drawable.error_box)

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        registerBtn.setOnClickListener {
            if (nameText.text.toString().isEmpty() || email.text.toString().isEmpty() || location.text.toString()
                    .isEmpty() || organnization.text.toString().isEmpty() || departmentList.isNullOrEmpty()) {
                if (nameText.text.toString().isEmpty()) {
                    nameText.setBackgroundResource(R.drawable.error_box)
                } else {
                    nameText.setBackgroundResource(R.drawable.box_stroke)

                }
                if (email.text.toString().isEmpty()) {
                    email.setBackgroundResource(R.drawable.error_box)
                } else {
                    email.setBackgroundResource(R.drawable.box_stroke)

                }

                if (location.text.toString().isEmpty()) {
                    location.setBackgroundResource(R.drawable.error_box)
                } else {
                    location.setBackgroundResource(R.drawable.box_stroke)

                }

                if (organnization.text.toString().isEmpty()) {
                    organnization.setBackgroundResource(R.drawable.error_box)
                } else {
                    organnization.setBackgroundResource(R.drawable.box_stroke)

                }
            } else {
                if (departmentList.isNullOrEmpty()) {
                    department.setBackgroundResource(R.drawable.error_box)

                } else {
                    department.setBackgroundResource(R.drawable.box_stroke)

                    for ((index, value) in departmentList.withIndex()) {
                        println("the element at $index is $value")
                        departmentVal = if (index > 0) {
                            departmentVal + " , " + value
                        } else {
                            value

                        }
                        Log.d("deparmentVal", departmentVal)

                    }
                    Log.d("deparmentValfinal", departmentVal)

                }

                val user = Users(
                    nameText.text.toString(),
                    email.text.toString(),
                    currentUser?.phoneNumber.toString(),
                    location.text.toString(),
                    organnization.text.toString(),
                    departmentVal,
                    "",
                    "admin",
                    ""
                ,""
                )
                val db = DBHandler(activity as AppCompatActivity)
                val result: Boolean = db.insertUsers(user)
                if (result) {
                  Snackbar.make(
                        it, "Registered Successfully",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show()
                    findNavController().navigate(R.id.addDetails_to_dashBoard)
                }
            }


        }

        return view
    }
    private fun ChipGroup.addChip(context: Context, label: String){
        Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = true
            isCheckable = true
            isCheckedIconVisible = false
            isFocusable = true
            addView(this)
        }
    }
}