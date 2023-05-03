package com.example.emp
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "employee.db"
        private const val TBL_EMPLOYEE  = "tbl_employee"
        private const val ID = "id"
        private const val EID= "eid"
        private const val COMPANY_NAME= "company_name"
        private const val ADDRESS= "address"
        private const val TP= "tp"
        private const val PASSWORD= "password"
    }

    override fun onCreate(db:SQLiteDatabase?){
        val createTblEmployee =
                ("CREATE TABLE " + TBL_EMPLOYEE +
                    "("  + ID +" INTEGER PRIMARY KEY," + EID + " STRING," + COMPANY_NAME +" TEXT,"
                         + ADDRESS + " STRING," + TP + " STRING," + PASSWORD + " STRING" +         ")"
                )

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
        contentValues.put(ID,emp.id)
        contentValues.put(EID,emp.eid)
        contentValues.put(COMPANY_NAME,emp.company_name)
        contentValues.put(ADDRESS,emp.address)
        contentValues.put(TP,emp.tp)
        contentValues.put(PASSWORD,emp.password)

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

        var id:Int
        var eid:String
        var company_name:String
        var address:String
        var tp:String
        var password:String

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                eid= cursor.getString(cursor.getColumnIndex("eid"))
                company_name= cursor.getString(cursor.getColumnIndex("company_name"))
                address= cursor.getString(cursor.getColumnIndex("address"))
                tp= cursor.getString(cursor.getColumnIndex("tp"))
                password= cursor.getString(cursor.getColumnIndex("password"))

                val emp =EmployeeModel(
                    id = id, eid = eid, company_name = company_name,
                    address = address, tp = tp, password = password )

                empList.add(emp)

            }while (cursor.moveToNext())
        }

        return empList
    }


}