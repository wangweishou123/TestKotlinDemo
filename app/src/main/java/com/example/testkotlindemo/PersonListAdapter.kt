package com.example.testkotlindemo

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_person.view.*

class PersonListAdapter(listData:ArrayList<Student>) :RecyclerView.Adapter<PersonListAdapter.ViewHolder>() {
    val listData = listData
//    var listData:ArrayList<Student>?=null
//    constructor(student: ArrayList<Student>) : this() {
//        listData=student
//    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(p0.context,R.layout.item_person,null))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0.itemView){
            name_tv_item.text= listData.get(p1).name
            gender_tv_item.text= listData.get(p1).gender
        }
    }

    class ViewHolder(item:View):RecyclerView.ViewHolder(item)

}