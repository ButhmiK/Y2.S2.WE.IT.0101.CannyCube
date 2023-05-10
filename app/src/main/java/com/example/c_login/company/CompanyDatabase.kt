package com.example.c_login.company

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class CompanyDatabase(context:Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION){

    companion object{

        private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="company.db"
        private const val TBL_COMPANY="tbl_company"

        private const val CNAME="cname"
        private const val CID="cid"
        private const val LOCATION="location"
        private const val TELEPHONE="telephone"
        private const val YEAR="year"


    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTblCompany = ("CREATE TABLE " + TBL_COMPANY + "("
                + CID + " INTEGER PRIMARY KEY, "
                + CNAME + " TEXT, "
                + LOCATION + " TEXT, "
                + TELEPHONE + " INTEGER, "
                + YEAR + " INTEGER" + ")"
                )

        db?.execSQL(createTblCompany)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_COMPANY")
        onCreate(db)
    }

    fun insertCompany(cmp: CompanyModel): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues ()
        contentValues.put(CID, cmp.cid )
        contentValues.put(CNAME, cmp.cname )
        contentValues.put(LOCATION, cmp.location )
        contentValues.put(TELEPHONE, cmp.telephone )
        contentValues.put(YEAR, cmp.year )

        val success = db.insert(TBL_COMPANY,null,contentValues)
        db.close()
        return success
    }

    fun getAllCompany(): ArrayList<CompanyModel>{
        val cmpList:ArrayList<CompanyModel> = ArrayList()
        val selectQuery ="SELECT * FROM $TBL_COMPANY"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var cid:Int
        var cname:String
        var location: String
        var telephone:String
        var year:String

        if (cursor.moveToFirst()){
            do{
                cid = cursor.getInt(cursor.getColumnIndexOrThrow("cid"))
                cname =cursor.getString(cursor.getColumnIndexOrThrow("cname"))
                location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                telephone = cursor.getString(cursor.getColumnIndexOrThrow("telephone"))
                year = cursor.getString(cursor.getColumnIndexOrThrow("year"))

                val cmp = CompanyModel(cid=cid, cname=cname,location=location,telephone=telephone,year=year)
                cmpList.add(cmp)
            }while (cursor.moveToNext())
        }
        return cmpList
    }

    fun updateCompany(cmp: CompanyModel) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CID, cmp.cid)
        contentValues.put(CNAME, cmp.cname)
        contentValues.put(LOCATION, cmp.location)
        contentValues.put(TELEPHONE, cmp.telephone)
        contentValues.put(YEAR, cmp.year)

        val success = db.update( TBL_COMPANY,contentValues," cid=" + cmp.cid,null )
        db.close()
        return success


    }

    fun deleteCompanyById(cid:Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CID, cid)

        val success= db.delete(TBL_COMPANY,"cid=$cid",null)
        db.close()
        return success
    }

}

