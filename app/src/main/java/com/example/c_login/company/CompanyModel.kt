package com.example.c_login.company

import java.util.Random

data class CompanyModel(
    var cid:Int = getAutoId(),
    var cname:String="",
    var location:String="",
    var telephone: String ="",
    var year: String ="",

    ) {

    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }

    }
}