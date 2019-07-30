package com.example.testkotlindemo.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.testkotlindemo.R
import kotlinx.android.synthetic.main.activity_person_list.*

class PersonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list)
        person_rv.layoutManager=LinearLayoutManager(this);
        var list:ArrayList<Student>?=ArrayList()
        if (list != null) {
            initData(list)
        }
        if (list != null) {
            Log.e("qwe","长度====>"+list.size)
        }else{
            Log.e("qwe","======>null")
        }
        person_rv.adapter= PersonListAdapter(list!!)
    }
    fun initData(list:ArrayList<Student>){
        list.add(Student("张三", "男"))
        list.add(Student("李四", "男"))
        list.add(Student("王舞", "女"))
        list.add(Student("赵柳", "女"))
        list.add(Student("小七", "男"))
        list.add (Student("张三*", "男"))
        list.add(Student("李四*", "男"))
        list.add(Student("王舞*", "女"))
        list.add(Student("赵柳*", "女"))
        list.add(Student("小七*", "男"))
    }
}
