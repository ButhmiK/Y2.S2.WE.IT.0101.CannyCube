
package com.example.emp
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//@Suppress("LocalVariableName")
class SQLiteHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "employee.db"
        private const val TBL_EMPLOYEE  = "tbl_employee"
        private const val Expenses= "expenses"
        private const val Week= "week"
        private const val Month= "month"

    }

    @SuppressLint("SQLiteString")
    override fun onCreate(db:SQLiteDatabase?){
        val createTblEmployee = ("CREATE TABLE " + TBL_EMPLOYEE +
                    "("  + Expenses + " STRING," + Week +" STRING,"
                         + Month + " STRING" +  ")" )

        db?.execSQL(createTblEmployee)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_EMPLOYEE")
        onCreate(db)
    }

    // insert employee

    fun insertEmployee(emp:EmployeeModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Expenses, emp.expenses)
        contentValues.put(Week,emp.week)
        contentValues.put(Month, emp.month)

        val success = db.insert(TBL_EMPLOYEE,null,contentValues)
        db.close()
        return success
    }
    // Read all employee

    @SuppressLint("Range")
    fun getALLEmployee(): ArrayList<EmployeeModel> {
        val empList: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EMPLOYEE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var expenses:String
        var week:String
        var month:String



        if (cursor.moveToFirst()){
            do{

                expenses= cursor.getString(cursor.getColumnIndex("expenses"))
                week= cursor.getString(cursor.getColumnIndex("week"))
                month= cursor.getString(cursor.getColumnIndex("month"))



                val emp =EmployeeModel(
                    expenses = expenses, week = week, month = month )

                empList.add(emp)

            }while (cursor.moveToNext())
        }

        return empList
    }

    fun updateEmployee(emp:EmployeeModel):Int{
        val db =this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Expenses, emp.expenses)
        contentValues.put(Week, emp.week)
        contentValues.put(Month, emp.month)

        val expenses1 = emp.expenses

        val success = db.update(TBL_EMPLOYEE, contentValues,"expenses='$expenses1'", null)
        db.close()
        return success

    }
    fun deleteEmployeeById(expenses:String): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Expenses,expenses)

        val success = db.delete(TBL_EMPLOYEE, "expenses='$expenses'",null)
        db.close()
        return success
    }


}