package com.example.signalcatcher
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.*
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)



        buttonEnter.setOnClickListener {
            val id= radioGender.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(id)

            Toast.makeText(this@UserInfoActivity, "You selected "
                    +radioButton.text, Toast.LENGTH_LONG).show()
        }
        buttonEnter.setOnClickListener(this)

        val ages = resources.getStringArray(R.array.ages)

        ageSpinner?.let {
            val ageAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, ages)
            ageSpinner.adapter = ageAdapter

            ageSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@UserInfoActivity,
                        getString(R.string.selected_item) + " " +
                                "" + ages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }


    override fun onClick(view: View?) {
        intent = Intent(this, ListActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
}
