package com.example.myapplication

import android.app.AlertDialog
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.history_fragment.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: InputFragment.OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        // レコードを入れるList
        var recordList = arrayListOf<MutableMap<String, String?>>()

        val fragment = InputFragment.newInstance()

        // DBからデータ取得
        val c: Cursor = getRecord()
        if (c.moveToFirst()) {
            // カーソルの一行目に移動して一件目のレコードを取得 以下繰り返し

            do {
                var recordMap: MutableMap<String, String?> = mutableMapOf()
                recordMap["日"] = c.getString(0)
                recordMap["身長"] = c.getString(1)
                recordMap["体重"] = c.getString(2)
                recordMap["BMI"] = c.getString(3)
                recordMap["コメント"] = c.getString(4)
                recordList.add(recordMap)
            } while (c.moveToNext())

            // データをViewに渡す
            val adapter = ListAdapter(recordList)
            recycler_view.adapter = adapter
            adapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {
                override fun onClick(view: View, data: MutableMap<String, String?>) {

                    var bundle = Bundle()

                    bundle.putString("日付", data["日"])
                    bundle.putString("身長", data["身長"])
                    bundle.putString("体重", data["体重"])
                    bundle.putString("BMI", data["BMI"])
                    bundle.putBoolean("削除", true)
                    bundle.putString("コメント", data["コメント"])

                    fragment.arguments = bundle

                    // 入力画面に遷移
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit()
                }

            })
        } else {
            AlertDialog.Builder(context).apply {
                setTitle("ERROR")
                setMessage("データが登録されていません")
                setPositiveButton("OK") { _, _ ->
                    Toast.makeText(
                        context,
                        "OK",
                        Toast.LENGTH_LONG
                    )
                }
                show()
                // 入力画面に遷移
                fragmentManager!!.beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit()
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun getRecord(): Cursor {
        return UserDbAdapter(context!!).getDB((arrayOf("insert_date", "height", "weight", "bmi", "comment")))
    }


}
