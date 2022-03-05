package com.example.attendancesystemmanagement

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME="AttendaceSysytemDb"
val TABLE_NAME="Users"
val COL_NAME="name"
val COL_EMAIL="email"
val COL_PHONE="number"
val COL_LOCATION="location"
val COL_ORGANIZATION="organization"
val COL_DEPARTMENT_TYPE="organization_department_type"
val COL_DEPARTMENT="department"
val COL_USERTYPE="user_type"
val COL_ID="id"
val COL_YEAR="year"
class DBHandler(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable="CREATE TABLE "+ TABLE_NAME+" ("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" VARCHAR(256),"+
                COL_EMAIL+" VARCHAR(256),"+
                COL_PHONE+" VARCHAR(256),"+
                COL_LOCATION+" VARCHAR(256),"+
                COL_ORGANIZATION+" VARCHAR(256),"+
                COL_DEPARTMENT_TYPE+" VARCHAR(256),"+
                COL_DEPARTMENT+" VARCHAR(256),"+
                COL_YEAR+" VARCHAR(256),"+
                COL_USERTYPE+" VARCHAR(256))"
                db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
 fun insertUsers(users: Users):Boolean {
  val db=this.writableDatabase
  val cv=ContentValues()
  cv.put(COL_NAME,users.name)
  cv.put(COL_EMAIL,users.email)
  cv.put(COL_PHONE,users.phone)
  cv.put(COL_LOCATION,users.location)
  cv.put(COL_DEPARTMENT,users.department)
  cv.put(COL_ORGANIZATION,users.organization)
  cv.put(COL_DEPARTMENT_TYPE,users.organization_department_type)
  cv.put(COL_USERTYPE,users.userType)
  val result=db.insert(TABLE_NAME,null,cv)
     if(result == -1.toLong()){
         Toast.makeText(context,"Fail to add",Toast.LENGTH_LONG).show()
         return false
     }
     else{
//         Toast.makeText(context,"Inserted Successfully",Toast.LENGTH_LONG).show()
          return true
     }
     db.close()

 }
    @SuppressLint("Range")
    fun getDepartmentList(query:String):String{
        val db=this.readableDatabase
        val result=db.rawQuery(query,null)
        var departmentVal=""
        if(result.moveToFirst()) {

            departmentVal= result.getString(result.getColumnIndex(COL_DEPARTMENT_TYPE)).toString()
        }
        result.close()
        db.close()
        return departmentVal
    }
    @SuppressLint("Range")
    fun getDetails(): ArrayList<Users> {
        val list:ArrayList<Users> = ArrayList()
        val db=this.readableDatabase
        val result=db.rawQuery("SELECT * FROM "+ TABLE_NAME,null)
        if(result.moveToFirst()){
            do{
             list.add(Users(result.getString(result.getColumnIndex(COL_NAME)).toString(),result.getString(result.getColumnIndex(COL_EMAIL)).toString(),
                 result.getString(result.getColumnIndex(COL_PHONE)).toString(),result.getString(result.getColumnIndex(COL_LOCATION)).toString(),
                 result.getString(result.getColumnIndex(COL_ORGANIZATION)).toString(),result.getString(result.getColumnIndex(COL_DEPARTMENT_TYPE)).toString(),
                 result.getString(result.getColumnIndex(COL_DEPARTMENT)).toString(),result.getString(result.getColumnIndex(COL_USERTYPE)).toString(),"null"
                 ,result.getString(result.getColumnIndex(COL_ID)).toString()))

            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    @SuppressLint("Range", "Recycle")
    fun getUserList(type:String) : ArrayList<Users>{
     val list:ArrayList<Users> = ArrayList()
        val db=this.readableDatabase

        val result=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE "+COL_USERTYPE + " = "+ "'"+type+"'",null)
            if(result.moveToFirst()){
                do{
                    list.add(Users(result.getString(result.getColumnIndex(COL_NAME)).toString(),result.getString(result.getColumnIndex(COL_EMAIL)).toString(),
                        result.getString(result.getColumnIndex(COL_PHONE)).toString(),result.getString(result.getColumnIndex(COL_LOCATION)).toString(),
                        result.getString(result.getColumnIndex(COL_ORGANIZATION)).toString(),result.getString(result.getColumnIndex(COL_DEPARTMENT_TYPE)).toString(),
                        result.getString(result.getColumnIndex(COL_DEPARTMENT)).toString(),result.getString(result.getColumnIndex(COL_USERTYPE)).toString(),"null"
                    ,result.getString(result.getColumnIndex(COL_ID)).toString()))

                }while (result.moveToNext())
            }

      return list
    }
    @SuppressLint("Range", "Recycle")
    fun getUserData(id:String) : ArrayList<Users>{
        val list:ArrayList<Users> = ArrayList()
        val db=this.readableDatabase

        val result=db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE "+ COL_ID + " = "+ "'"+id+"'",null)
        if(result.moveToFirst()){
            do{
                list.add(Users(result.getString(result.getColumnIndex(COL_NAME)).toString(),result.getString(result.getColumnIndex(COL_EMAIL)).toString(),
                    result.getString(result.getColumnIndex(COL_PHONE)).toString(),result.getString(result.getColumnIndex(COL_LOCATION)).toString(),
                    result.getString(result.getColumnIndex(COL_ORGANIZATION)).toString(),result.getString(result.getColumnIndex(COL_DEPARTMENT_TYPE)).toString(),
                    result.getString(result.getColumnIndex(COL_DEPARTMENT)).toString(),result.getString(result.getColumnIndex(COL_USERTYPE)).toString(),"null"
                    ,result.getString(result.getColumnIndex(COL_ID)).toString()))

            }while (result.moveToNext())
        }

        return list
    }

}