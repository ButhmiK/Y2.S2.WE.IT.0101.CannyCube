package com.example.c_login

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.c_login.income.StudentModel

class DataBaseHelper (context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME="cannycube.db"
        private const val DATABASE_VERSION = 2
        private const val TBL_USER="user_table"
        private const val USERNAME = "username"
        private const val PASSWORD_ONE ="password_one"
        private const val PASSWORD_TWO = "password_two"

        private const val TBL_STUDENT ="tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE $TBL_USER (username TEXT PRIMARY KEY, password_one TEXT," +
                "password_two TEXT)")

        val createTblStudent =
            ("CREATE TABLE " + TBL_STUDENT + "("
                    + ID + " PRIMARY KEY," + NAME + " TEXT,"
                    + EMAIL +" TEXT"+ ")")

        db?.execSQL(createTblStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate(db)
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }




    fun getAllUsers():ArrayList<UserModel>{
        val userArray : ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"

        val db = this.readableDatabase
        val cursor: Cursor?


        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e: Exception){

            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var username: String
        var password_one: String
        var password_two: String

        if (cursor.moveToFirst()){
            do{
                username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                password_one = cursor.getString(cursor.getColumnIndexOrThrow("password_one"))
                password_two = cursor.getString(cursor.getColumnIndexOrThrow("password_two"))


                val user = UserModel(username=username, password_one=password_one, password_two=password_two)
                userArray.add(user)


            }while(cursor.moveToNext())

        }
        return userArray
    }

    fun insertUserFunction(username:String,password:String,comPassword:String):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username",username)
        cv.put(PASSWORD_ONE,password)
        cv.put(PASSWORD_TWO,comPassword)
        val result = db.insert(TBL_USER,null,cv)

        if (result == -1.toLong()){
            return false
        }
        return true
    }

    fun userValidationFunction(username:String,password:String):Boolean{
        var isTrue :Boolean
        val db = this.writableDatabase
        val query = "SELECT * FROM $TBL_USER WHERE username= '$username' AND password_one= '$password'"
        val cursor = db.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            isTrue = false
        }else{
            cursor.close()
            isTrue = true
        }

        return isTrue

    }


    fun insertStudent(std: StudentModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(EMAIL,std.email)

        val success = db.insert(TBL_STUDENT,null, contentValues)
        db.close()
        return success
    }


    fun getAllStudent(): ArrayList<StudentModel> {

        val stdList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: String
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))

                val std = StudentModel(id = id, name = name, email = email)
                stdList.add(std)

            } while (cursor.moveToNext())

        }
        return stdList
    }

    fun updateStudent(std: StudentModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)

        val sid = std.id;

        val success = db.update(TBL_STUDENT, contentValues, "id= '$sid'", null)
        db.close()
        return success

    }

    fun deleteStudentById(id: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT, "id='$id'", null)
        db.close()
        return success
    }
    }



























