package com.example.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.history_fragment.view.*
import kotlinx.android.synthetic.main.one_record.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val myDataSet: MutableList<MutableMap<String, String?>>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onClick(view: View,data:MutableMap<String,String?>)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    // 現在の月 最初は空
    private var currentMonth = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val recycler = LayoutInflater.from(p0.context).inflate(R.layout.one_record, p0, false) as LinearLayout
        return ViewHolder(recycler)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var date: Date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(myDataSet[position]["日"])
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)
        calendar.time = date
        var month = calendar.get(Calendar.MONTH).plus(1).toString()

        holder.itemView.setOnClickListener {
            listener.onClick(it, myDataSet[position])
        }

        // 月が変わったらセクションを表示
        if (!(month.equals(currentMonth))) {
            holder.itemView.month_label.visibility = View.VISIBLE
            holder.itemView.month_label.month.text = "$month 月"
            currentMonth = month
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

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}



