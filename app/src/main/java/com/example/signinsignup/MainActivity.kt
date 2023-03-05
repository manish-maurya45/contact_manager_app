package com.example.signinsignup

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var dialog : Dialog
    lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)

        var new_contact_btn = findViewById<CardView>(R.id.new_contact_cardV)
        var contact_name_edt = dialog.findViewById<TextInputEditText>(R.id.contact_name_edt)
        var phone_edt = dialog.findViewById<TextInputEditText>(R.id.phone_edt)
        var add_btn = dialog.findViewById<AppCompatButton>(R.id.add_btn)



        new_contact_btn.setOnClickListener {
            dialog.show()
        }

        add_btn.setOnClickListener {
            val contact_name = contact_name_edt.text.toString()
            val phone = phone_edt.text.toString()
            val contact = ContactDetails(contact_name,phone)
            db = FirebaseDatabase.getInstance().getReference("Contacts")
            db.child(phone).setValue(contact).addOnSuccessListener {
                Toast.makeText(this,"Contact Saved Successfully", Toast.LENGTH_SHORT).show()
                val view = layoutInflater.inflate(R.layout.contact_card_cv,null)
                val layout_holder = findViewById<LinearLayout>(R.id.container_ll)
                layout_holder.addView(view)

                val name_of_contact = view.findViewById<TextView>(R.id.contact_name_tv)
                val phone_number = view.findViewById<TextView>(R.id.phone_number_tv)
                val call_btn = view.findViewById<ImageView>(R.id.call_btn)

                name_of_contact.text = contact_name
                phone_number.text = phone

                //clearing edit text content
                contact_name_edt.text?.clear()
                phone_edt.text?.clear()
                
                call_btn.setOnClickListener() {
                    if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),101)
                    }
                    val phone_number = phone_number.text.toString()
                    val i = Intent()
                    i.action = Intent.ACTION_CALL
                    i.data = Uri.parse("tel:$phone_number")
                    startActivity(i)
//                    Toast.makeText(this,"Dimag Kharab kr diya", Toast.LENGTH_SHORT).show()

                }
                dialog.dismiss()

            }.addOnFailureListener {
                Toast.makeText(this,"Contact Not Saved, Some Error Orrured", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

    }
}