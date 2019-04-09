package com.example.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.one_record.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val myDataSet: MutableList<MutableMap<String, String?>>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return myDataSet.size
    }

    class ViewHolder(linear: LinearLayout) : RecyclerView.ViewHolder(linear)

    // 現在の月 最初は空
    var current_month = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListAdapter.ViewHolder {
        val linear = LayoutInflater.from(p0.context).inflate(R.layout.one_record, p0, false) as LinearLayout
        return ViewHolder(linear)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        var date: Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(myDataSet[position]["日"])
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)
        calendar.time = date
        var month = calendar.get(Calendar.MONTH).plus(1).toString()


        if (!(month.equals(current_month))) {
            holder.itemView.month_label.visibility = View.VISIBLE
            holder.itemView.month_label.month.text = "$month 月"
            current_month = month
        } else {
            holder.itemView.month_label.visibility = View.GONE
        }
        holder.itemView.record_date.text = calendar.get(Calendar.DAY_OF_MONTH).toString() + "日"
        holder.itemView.record_height.text = "身長 " + myDataSet[position]["身長"] + "cm"
        holder.itemView.record_weight.text = "体重 " + myDataSet[position]["体重"] + "kg"
        holder.itemView.record_bmi.text = "BMI " + myDataSet[position]["BMI"]
        if (!(myDataSet[position]["コメント"].isNullOrEmpty())) {
            holder.itemView.record_comment.text = myDataSet[position]["コメント"]
        } else {
            holder.itemView.linear_comment.visibility = View.GONE
        }

    }
}



