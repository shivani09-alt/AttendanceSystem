package com.example.attendancesystemmanagement

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth




class AddStudentDetails : Fragment() {

    private val TABLE_NAME="Users"
    private val COL_PHONE="number"
    lateinit var auth: FirebaseAuth
    var dept:String=""
    var yearOfStudent:String=""

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_add_student_details, container, false)
        auth=FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val yearSelected= arrayListOf<String>()
        val departmentSelected= arrayListOf<String>()
        (activity as AppCompatActivity).supportActionBar?.title = "Add Staff"
         val nameText:TextInputEditText=view.findViewById(R.id.name)
         val phoneNumber:TextInputEditText=view.findViewById(R.id.phoneNumber)
         val email:TextInputEditText=view.findViewById(R.id.email)
         val addStudent:Button=view.findViewById(R.id.addStudent)
         val yearLayout:RadioGroup=view.findViewById(R.id.yearLayout)
         val departmentLayout:RadioGroup=view.findViewById(R.id.departmentLayout)
         val yearError:TextView=view.findViewById(R.id.yearError)
         val departmentError:TextView=view.findViewById(R.id.departmentError)
        // Add CheckBox to LinearLayout

        val year = mutableListOf<String>()
         year.add("FY")
         year.add("SY")
         year.add("TY")


        email.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length>0) {
                    email.setBackgroundResource(R.drawable.box_stroke)
                }



            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        phoneNumber.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameText.setBackgroundResource(R.drawable.box_stroke)
                if(p0.toString().length>0) {
                    phoneNumber.setBackgroundResource(R.drawable.box_stroke)
                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        nameText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nameText.setBackgroundResource(R.drawable.box_stroke)
                if(p0.toString().length>0) {
                    nameText.setBackgroundResource(R.drawable.box_stroke)
                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        val db=DBHandler(activity as AppCompatActivity)
        val number=currentUser?.phoneNumber.toString()
        val departmentVal= db.getDepartmentList("Select * from "+TABLE_NAME +" where "+ COL_PHONE + " = '" + number +"'")
        Log.d("departmentVal",departmentVal)
        val splitDepartment=departmentVal.split(",").toTypedArray()
        for(item in splitDepartment){
            val radioButton=RadioButton(activity)
            radioButton.setHint(item)
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.setPadding(20, 20, 20, 20)
            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
              departmentSelected.add(item)
                departmentError.visibility=View.GONE
                dept=radioButton.text.toString()
            }

            // Add CheckBox to LinearLayout
            radioButton.setButtonTintList(getColorStateList(activity as AppCompatActivity,R. color.theme))
            departmentLayout.addView(radioButton)

        }
        for(item in  year){
            val radioButton=RadioButton(activity)
            radioButton.setHint(item)
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.setPadding(20, 20, 20, 20)
            radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
               yearSelected.add(item)
               yearError.visibility=View.GONE
                yearOfStudent=radioButton.text.toString()


            }
            radioButton.setButtonTintList(getColorStateList(activity as AppCompatActivity,R. color.theme))
            yearLayout.addView(radioButton)

        }

        addStudent.setOnClickListener {
            if (nameText.text.toString().length == 0 || email.text.toString().length == 0 || departmentSelected.isNullOrEmpty() || yearSelected.isNullOrEmpty()) {
                if (nameText.text.toString().length == 0) {
                    nameText.setBackgroundResource(R.drawable.error_box)
                } else {
                    nameText.setBackgroundResource(R.drawable.box_stroke)

                }
                if (email.text.toString().length == 0) {
                    email.setBackgroundResource(R.drawable.error_box)
                } else {
                    email.setBackgroundResource(R.drawable.box_stroke)

                }
                if (phoneNumber.text.toString().length == 0) {
                    phoneNumber.setBackgroundResource(R.drawable.error_box)
                } else {
                    phoneNumber.setBackgroundResource(R.drawable.box_stroke)

                }
                if (departmentSelected.isNullOrEmpty()) {
                    departmentError.visibility = View.VISIBLE

                } else {
                    departmentError.visibility = View.GONE

                }
                if (yearSelected.isNullOrEmpty()) {
                    yearError.visibility = View.VISIBLE

                } else {
                    yearError.visibility = View.GONE

                }


            } else {
                val user = Users(
                    nameText.text.toString(),
                    email.text.toString(),
                    phoneNumber.text.toString(),
                    "",
                    "",
                    "",
                    dept,
                    "student",
                    yearOfStudent
                ,""
                )
                val db = DBHandler(activity as AppCompatActivity)
                val result: Boolean = db.insertUsers(user)
                if (result == true) {
                    nameText.setText("")
                    email.setText("")
                    phoneNumber.setText("")
                    yearLayout.clearCheck()
                    departmentLayout.clearCheck()
                    Snackbar.make(
                        it, "Added Successfully",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).show()
                }

            }

        }
        return view
    }

}