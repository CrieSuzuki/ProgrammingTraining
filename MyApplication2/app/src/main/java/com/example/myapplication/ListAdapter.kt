package com.example.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.one_record.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(private val myDataSet: MutableList<MutableMap<String, String?>>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    lateinit var listener: OnItemClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onClick(view: View, data: MutableMap<String, String?>)
    }

    private val util = Util()

    // 現在の月 最初は空
    private var currentMonth = ""


    override fun getItemCount(): Int {
        return myDataSet.size
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val recycler = LayoutInflater.from(p0.context).inflate(R.layout.one_record, p0, false) as LinearLayout
        return ViewHolder(recycler)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var (month, date) = util.getDateAndMonth(SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(myDataSet[position]["日"]))

        holder.itemView.setOnClickListener {
            listener.onClick(it, myDataSet[position])
        }

        createCell(holder,position,month,date,currentMonth)

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 表示するセルの作成
    private fun createCell(holder: ViewHolder,position:Int, month: String, date:String, currentMonth: String) {
        if (month != currentMonth) {
            holder.itemView.month_label.visibility = View.VISIBLE
            holder.itemView.month_label.month.text = "$month 月"
            this.currentMonth = month
        } else {
            holder.itemView.month_label.visibility = View.GONE
        }
        holder.itemView.record_date.text = date +"日"
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



