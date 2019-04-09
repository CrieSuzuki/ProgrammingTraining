package com.example.myapplication

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // DBからデータ取得
        val c: Cursor = getRecord()
        // レコードを入れるList
        var recordList = arrayListOf<MutableMap<String, String?>>()


        // カーソルの一行目に移動して一件目のレコードを取得
        c.moveToFirst()
        do{
            // レコードの各カラムの値を保持するMap
            var recordMap: MutableMap<String, String?> = mutableMapOf()
            recordMap["日"] = c.getString(0)
            recordMap["身長"] = c.getString(1)
            recordMap["体重"] = c.getString(2)
            recordMap["BMI"] = c.getString(3)
            recordMap["コメント"] = c.getString(4)
            recordList.add(recordMap)
        }while(c.moveToNext())
//
//        // 二行目以降も同じ
//        while (c.moveToNext()) {
//            var dataMap: MutableMap<String, String?> = mutableMapOf()
//            dataMap["日"] = c.getString(0)
//            dataMap["身長"] = c.getString(1)
//            dataMap["体重"] = c.getString(2)
//            dataMap["BMI"] = c.getString(3)
//            dataMap["コメント"] = c.getString(4)
//            recordList.add(dataMap)
//            c.moveToNext()
//        }
        // データをViewに渡す
        recycler_view.adapter = ListAdapter(recordList)

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputFragment.
         */
        // TODO: Rename and change types and number of parameters
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
