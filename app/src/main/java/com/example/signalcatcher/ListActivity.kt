package com.example.signalcatcher

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*
import java.time.LocalDateTime
import java.util.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        //var itemList = mutableListOf<Item>()
        var list = mutableListOf<String>()
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = arrayAdapter

        btn_add.setOnClickListener {
            Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
            list_empty.visibility = View.GONE
            val cal = Calendar.getInstance()
            val now : String = "${cal.get(Calendar.YEAR)}.${cal.get(Calendar.MONTH)+1}.${cal.get(Calendar.DATE)} ${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
            list.add(now)
            //list.add("이름")
            arrayAdapter.notifyDataSetChanged()
        }
        btn_option.setOnClickListener {
            Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
        }
        list_empty.setOnClickListener {
            Toast.makeText(this, "우측 상단의 버튼으로 대화를 추가해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}