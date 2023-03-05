package com.example.signinsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {

    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val edtUserName = findViewById<TextInputEditText>(R.id.edtUserName)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val tv_signUp = findViewById<TextView>(R.id.tv_signUp)

        btnLogin.setOnClickListener {
            val user_name = edtUserName.text.toString()
            val password = edtPassword.text.toString()
            if(user_name.isNotEmpty() && password.isNotEmpty())
                authenticate(user_name,password)
            else{
                if (user_name.isEmpty())
                    Toast.makeText(this,"user name field can't be empty",Toast.LENGTH_SHORT).show()
                else if(password.isEmpty())
                    Toast.makeText(this,"password field can't be empty",Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this,"Enter user name and password first then click on login",Toast.LENGTH_SHORT).show()
            }
        }


        tv_signUp.setOnClickListener {
            val i = Intent(this,SignUp::class.java)
            startActivity(i)
        }
    }

    private fun authenticate(user_name: String, password: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(user_name).get().addOnSuccessListener {
            if(it.exists()){
                val user_name_db = it.child("userName").value
                val password_db = it.child("password").value
                if(user_name.equals(user_name_db) && password.equals(password_db)){
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                else{
                    Toast.makeText(this,"incorrect password",Toast.LENGTH_SHORT).show()

                }
            }
            else
                Toast.makeText(this,"user does not exist, sign up first",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Some Error occured while fetching the data from firebase database",Toast.LENGTH_SHORT).show()
        }
    }
}