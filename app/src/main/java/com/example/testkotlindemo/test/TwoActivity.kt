package com.example.testkotlindemo.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.testkotlindemo.R
import kotlinx.android.synthetic.main.activity_two.*

class TwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        val student= intent.getSerializableExtra("student") as Student
        val name = "\n姓名:"
        user_info_tv.text = name.plus("${student.name}").plus("\n年龄:${student.age}").plus("\n性别:${student.gender}")
            .plus(" \n学号:${student.studentId}")
        btn_look_all.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, PersonListActivity::class.java)
            startActivity(intent)
        }
    }
}