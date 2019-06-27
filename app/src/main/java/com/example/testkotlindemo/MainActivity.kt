package com.example.testkotlindemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val userName="admin"
    val password="123456"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_btn.setOnClickListener {
            when(checkUserNameAndPwd(user_name_edit.text.toString(),pwd_edit.text.toString())){
                1-> jumpActivity()
                2-> Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT).show()
                3-> Toast.makeText(this,"帐号错误",Toast.LENGTH_SHORT).show()
                4-> Toast.makeText(this,"账号密码错误",Toast.LENGTH_SHORT).show()
                5-> Toast.makeText(this,"账号不能为空",Toast.LENGTH_SHORT).show()
                6-> Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show()
                7-> Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_SHORT).show()
            }
//            if (checkUserNameAndPwd(user_name_edit.text.toString(),pwd_edit.text.toString())==1){
//                jumpActivity()
//            }else{
//                Toast.makeText(this,"账号密码错误",Toast.LENGTH_SHORT).show()
//            }
        }
    }
    fun jumpActivity(){
        var jumpIntent =Intent()
        var student=Student()
//        student.nickName=user_name_edit.text.toString()
//        student.passWord=pwd_edit.text.toString()
        student.age=40
        student.gender="男"
        student.name="Tony Stark"
        student.studentId=101

//        jumpIntent.putExtra("hello","this is Tony Stark")
        jumpIntent.putExtra("student",student)
        jumpIntent.setClass(this,TwoActivity::class.java);
        startActivity(jumpIntent)
    }
    fun checkUserNameAndPwd(name:String, pwd:String) :Int{
        if (!TextUtils.isEmpty(name.trim())&&!TextUtils.isEmpty(pwd.trim())){
            if (name.equals(userName)&&pwd.equals(password)){
                return 1
            }else if (name.equals(userName)&&!pwd.equals(password)){
                return 2
            }else if (!name.equals(userName)&&pwd.equals(password)){
                return 3
            }else if (!name.equals(userName)&&!pwd.equals(password)){
                return 4
            }
        }else if (TextUtils.isEmpty(name.trim())&&!TextUtils.isEmpty(pwd.trim())){
            return 5
        }else if (!TextUtils.isEmpty(name.trim())&&TextUtils.isEmpty(pwd.trim())){
            return 6
        }else if (!TextUtils.isEmpty(name.trim())&&!TextUtils.isEmpty(pwd.trim())){
            return 7
        }
        return 0
    }
}
