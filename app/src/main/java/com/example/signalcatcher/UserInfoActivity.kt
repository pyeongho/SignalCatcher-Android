package com.example.signalcatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class UserInfoActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var radioGroup: RadioGroup
    lateinit var buttonEnter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        radioGroup = findViewById(R.id.radioGender)
        buttonEnter = findViewById(R.id.buttonEnter)

        findViewById<Button>(R.id.buttonEnter).setOnClickListener {
            val id= radioGroup.checkedRadioButtonId

            val radioButton = findViewById<RadioButton>(id)

            Toast.makeText(this@UserInfoActivity, "You selected "
                    +radioButton.text, Toast.LENGTH_LONG).show()


        }
        buttonEnter.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        intent = Intent(this, MainActivity::class.java)
        // start your next activity
        startActivity(intent)

    }
}
