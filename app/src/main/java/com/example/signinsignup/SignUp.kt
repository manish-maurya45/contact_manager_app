package com.example.signinsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val edtName = findViewById<TextInputEditText>(R.id.edtName)
        val edtMail = findViewById<TextInputEditText>(R.id.edtMail)
        val edtUserName = findViewById<TextInputEditText>(R.id.edtUserName)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        val btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val mail = edtMail.text.toString()
            val userName = edtUserName.text.toString()
            val password = edtPassword.text.toString()
            val user = Users(name,mail,userName,password)

            database = FirebaseDatabase.getInstance().getReference("users")
            database.child(userName).setValue(user).addOnSuccessListener {
                Toast.makeText(this,"user Register Successfully", Toast.LENGTH_SHORT).show()
                val i = Intent(this, SignIn::class.java)
                startActivity(i)
                finish()
            }
        }

    }
}