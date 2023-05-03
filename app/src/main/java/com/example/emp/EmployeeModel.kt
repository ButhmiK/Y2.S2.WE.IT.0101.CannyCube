package com.example.emp

import java.util.*

//import kotlin.random.Random

data class EmployeeModel(
    var id:Int = getAutoId(),
    var eid:String ="",
    var name:String = "",
    var company_name:String = "",
    var address:String = "",
    var tp: String = "",
    var password:String = "",
){
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }

    }

}
