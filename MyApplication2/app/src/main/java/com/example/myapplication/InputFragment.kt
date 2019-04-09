package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_input.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InputFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class InputFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_input, container, false)
    }


    /**
     * fragment生成後の処理
     * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var height: String
        var weight: String
        var bmi: String
        var comment: String


        // 入力欄に対する設定
        configureEditText(input_height)
        configureEditText(input_weight)

        // 計算ボタンのリスナー設定
        calculate_button.setOnClickListener {

            height = input_height.text.toString()
            weight = input_weight.text.toString()

            // 入力チェック
            if (isCorrectInput(height, weight)) {
                bmi = calculateLogic(height, weight)
                // 表示
                result_message.text = "あなたのBMIは $bmi でした。"

                //SharedPreferenceに登録
                val dataStore: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context)
                val editor = dataStore.edit()
                editor.putString("身長", height)
                editor.putString("体重", weight)
                editor.apply()

                //コメント欄と保存ボタンを見えるようにする
                input_excuse.visibility = View.VISIBLE
                save_button.visibility = View.VISIBLE

                //保存ボタンのリスナー設定
                save_button.setOnClickListener {
                    comment = input_excuse.text.toString()
                    saveData(height, weight, bmi, comment)
                }

                //削除ボタンのリスナー設定
                //TODO:履歴画面作成後に適切な場所に移動
//                delete_button.setOnClickListener {
//                    deleteData()
//                }
            } else {
                makeAlert("身長、体重を入力してください")
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

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
            InputFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    /**
     * BMI計算処理
     * */
    private fun calculateLogic(height: String, weight: String): String {


        // BMI計算して返却
        val heightMeter = height.toDouble() * 0.01
        val result = weight.toDouble() / (heightMeter * heightMeter)
        return "%.1f".format(result)
    }


    /**
     * 保存ボタン押下時の処理内容
     * */
    private fun saveData(
        height: String,
        weight: String,
        bmi: String,
        comment: String
    ) {
        val userDb = UserDbAdapter(this.context!!)
        userDb.add(height, weight, bmi, comment)
    }

    /**
     * 削除ボタン押下時の処理内容
     * */
    private fun deleteData() {
        // 今は入力、出力値をクリアするだけ
        // TODO:履歴画面から選択したレコードを削除する処理に変更
        input_height.text.clear()
        input_weight.text.clear()
        result_message.text = ""
        input_excuse.text.clear()
    }

    /**
     * 入力値をチェックする
     * */
    private fun isCorrectInput(a: String, b: String): Boolean {
        return !(a.isEmpty() || b.isEmpty())
    }


    /**
     * アラートダイアログを作って表示する
     * */
    private fun makeAlert(alertMessage: String) {
        AlertDialog.Builder(context).apply {
            setTitle("ERROR")
            setMessage(alertMessage)
            setPositiveButton(
                "OK",
                DialogInterface.OnClickListener { _, _ ->
                    Toast.makeText(
                        context,
                        "OK",
                        Toast.LENGTH_LONG
                    )
                })
            show()
        }
    }

    /**
     * 身長、体重欄に対する設定
     * */
    private fun configureEditText(editText: EditText) {
        val filter = InputFilter{source,start,end,dest,dstart,dend ->
            // TODO 正規表現見直し
            if (source.toString().matches("(^([0-9]{0,3})?(\\.[0-9]?)?$)".toRegex())) source
            else ""
        }
        editText.setFilters(arrayOf(filter))

    }
}
