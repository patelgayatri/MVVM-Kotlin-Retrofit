package com.techand.sampletest.data.models

data class User (
    val id: Int,
    val name: String,
    val userName: String,
    val email: String,
    val phone : String,
    val website : String
){
    fun name_str(): String {
        return "Name: $name"
    }
    fun email_str(): String {
        return "Email: $email"
    }
    fun phone_str(): String {
        return "Phone: $phone"
    }
}
