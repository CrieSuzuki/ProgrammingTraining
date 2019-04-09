package com.example.myapplication

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.one_record.view.*

class ListAdapter(private val myDataSet: MutableList<MutableMap<String,String?>>):RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    class ViewHolder(val linear: LinearLayout):RecyclerView.ViewHolder(linear)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListAdapter.ViewHolder {
        val linear = LayoutInflater.from(p0.context).inflate(R.layout.one_record,p0,false) as LinearLayout
        return ViewHolder(linear)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.linear.record_date.text = myDataSet[position]["日"]
        holder.linear.record_height.text = myDataSet[position]["身長"]
        holder.linear.record_weight.text = myDataSet[position]["体重"]
        holder.linear.record_bmi.text= myDataSet[position]["BMI"]
        holder.linear.record_comment.text = myDataSet[position]["コメント"]

    }

    override fun getItemCount() = myDataSet.size


}