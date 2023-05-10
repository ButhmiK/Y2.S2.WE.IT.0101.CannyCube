package com.example.c_login.expense


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ExpenseDatabase(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "expense.db"
        private const val TBL_EXPENSE = "tbl_expense"
        private const val EXP_ID = "exp_id"
        private const val EXP_CATEGORY = "exp_category"
        private const val EXP_MONTH = "exp_month"
        private const val EXP_AMOUNT = "exp_amount"

    }


    override fun onCreate(db:SQLiteDatabase?) {
        val createTblExpense =
            ("CREATE TABLE " + TBL_EXPENSE + "("
                    + EXP_ID + " PRIMARY KEY," + EXP_CATEGORY + " TEXT,"
                    + EXP_MONTH +" TEXT," + EXP_AMOUNT + " TEXT"+ ")")

        db?.execSQL(createTblExpense)

    }

    override fun onUpgrade(db:SQLiteDatabase?, olVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_EXPENSE")
        onCreate(db)
    }

    fun insertStudent(exp: ExpenseModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EXP_ID,exp.exp_id)
        contentValues.put(EXP_CATEGORY,exp.exp_category)
        contentValues.put(EXP_MONTH,exp.exp_month)
        contentValues.put(EXP_AMOUNT,exp.exp_amount)

        val success = db.insert(TBL_EXPENSE,null, contentValues)
        db.close()
        return success
    }


    fun getAllStudent(): ArrayList<ExpenseModel> {

        val stdList: ArrayList<ExpenseModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EXPENSE"
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
                id = cursor.getString(cursor.getColumnIndexOrThrow("exp_id"))
                category = cursor.getString(cursor.getColumnIndexOrThrow("exp_category"))
                month = cursor.getString(cursor.getColumnIndexOrThrow("exp_month"))
                amount = cursor.getString(cursor.getColumnIndexOrThrow("exp_amount"))

                val std = ExpenseModel(exp_id = id, exp_category = category, exp_month = month, exp_amount = amount)
                stdList.add(std)

            } while (cursor.moveToNext())

        }
        return stdList
    }

    fun updateStudent(std: ExpenseModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EXP_ID, std.exp_id)
        contentValues.put(EXP_CATEGORY, std.exp_category)
        contentValues.put(EXP_MONTH, std.exp_month)
        contentValues.put(EXP_MONTH, std.exp_amount)

        val sid = std.exp_id;

        val success = db.update(TBL_EXPENSE, contentValues, "exp_id= '$sid'", null)
        db.close()
        return success

    }

    fun deleteStudentById(id: String): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(EXP_ID, id)

        val success = db.delete(TBL_EXPENSE, "exp_id='$id'", null)
        db.close()
        return success
    }

}