package com.example.c_login

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.c_login.income.IncomeModel
import com.example.c_login.login_company.UserModel

class DataBaseHelper (context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME="cannycube.db"
        private const val DATABASE_VERSION = 2
        private const val TBL_USER="user_table"
        private const val USERNAME = "username"
        private const val PASSWORD_ONE ="password_one"
        private const val PASSWORD_TWO = "password_two"

        private const val TBL_INCOME ="tbl_income"
        private const val INC_ID = "inc_id"
        private const val INC_CATEGORY = "inc_category"
        private const val INC_MONTH = "inc_month"
        private const val INC_AMOUNT = "inc_amount"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE $TBL_USER (username TEXT PRIMARY KEY, password_one TEXT," +
                "password_two TEXT)")

        val createTblIncome =
            ("CREATE TABLE " + TBL_INCOME + "("
                    + INC_ID + " PRIMARY KEY," + INC_CATEGORY + " TEXT,"
                    + INC_MONTH +" TEXT," + INC_AMOUNT + " TEXT"+ ")")

        db?.execSQL(createTblIncome)
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate(db)
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_INCOME")
        onCreate(db)
    }


    //Login database handling

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

    //Income Database handling

    fun insertIncome(inc: IncomeModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(INC_ID,inc.inc_id)
        contentValues.put(INC_CATEGORY,inc.inc_category)
        contentValues.put(INC_MONTH,inc.inc_month)
        contentValues.put(INC_AMOUNT,inc.inc_amount)

        val success = db.insert(TBL_INCOME,null, contentValues)
        db.close()
        return success
    }


    fun getAllIncome(): ArrayList<IncomeModel> {

        val incList: ArrayList<IncomeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_INCOME"
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
        var category: String
        var month: String
        var amount: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndexOrThrow("inc_id"))
                category = cursor.getString(cursor.getColumnIndexOrThrow("inc_category"))
                month = cursor.getString(cursor.getColumnIndexOrThrow("inc_month"))
                amount = cursor.getString(cursor.getColumnIndexOrThrow("inc_amount"))

                val inc = IncomeModel(inc_id = id, inc_category = category, inc_month = month, inc_amount = amount)
                incList.add(inc)

            } while (cursor.moveToNext())

        }
        return incList
    }

    fun updateIncome(inc: IncomeModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(INC_ID, inc.inc_id)
        contentValues.put(INC_CATEGORY, inc.inc_category)
        contentValues.put(INC_MONTH, inc.inc_month)
        contentValues.put(INC_AMOUNT, inc.inc_amount)

        val iid = inc.inc_id;

        val success = db.update(TBL_INCOME, contentValues, "inc_id= '$iid'", null)
        db.close()
        return success

    }

    fun deleteIncomeById(iid: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(INC_ID, iid)

        val success = db.delete(TBL_INCOME, "inc_id='$iid'", null)
        db.close()
        return success
    }
    }



























