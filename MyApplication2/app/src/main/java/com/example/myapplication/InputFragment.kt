package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_input.*

private const val ARG_PARAM_DATE = "日付"
private const val ARG_PARAM_HEIGHT = "身長"
private const val ARG_PARAM_WEIGHT = "体重"
private const val ARG_PARAM_BMI = "BMI"
private const val ARG_PARAM_COMMENT = "コメント"


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
    private var paramDate: String? = null
    private var paramHeight: String? = null
    private var paramWeight: String? = null
    private var paramBmi: String? = null
    private var paramComment: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramDate = it.getString(ARG_PARAM_DATE)
            paramHeight = it.getString(ARG_PARAM_HEIGHT)
            paramWeight = it.getString(ARG_PARAM_WEIGHT)
            paramBmi = it.getString(ARG_PARAM_BMI)
            paramComment = it.getString(ARG_PARAM_COMMENT)
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
        var height: String
        var weight: String
        var bmi: String
        var comment: String

        super.onViewCreated(view, savedInstanceState)


        // 入力欄に対する設定
        configureEditText(input_height)
        configureEditText(input_weight)

        // 削除時の設定
        if (arguments?.getBoolean("削除") == true) {
            prepareForDelete()
        }

        // 計算ボタンのリスナー設定
        calculate_button.setOnClickListener {

            height = input_height.text.toString()
            weight = input_weight.text.toString()


            // 入力チェック
            if (isCorrectInput(height, weight)) {
                bmi = util.calculateLogic(height, weight)
                // 表示
                result_message.text = "あなたのBMIは $bmi でした。"
                // sharedPreferencesに登録
                saveSharedPreferences(height,weight,bmi)
                //コメント欄と保存ボタンを見えるようにする
                input_excuse.visibility = View.VISIBLE
                save_button.visibility = View.VISIBLE
                //保存ボタンのリスナー設定
                save_button.setOnClickListener {
                    comment = input_excuse.text.toString()
                    saveData(height, weight, bmi, comment)
                }
            } else {
                util.makeAlert(context!!,"身長、体重を入力してください")
            }
        }
    }


    private fun saveSharedPreferences(h:String,w:String,bmi:String){
        //SharedPreferenceに登録
        val dataStore: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val editor = dataStore.edit()
        editor.putString("身長", h)
        editor.putString("体重", w)
        editor.putString("BMI",bmi)
        editor.apply()
    }
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InputFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    /**
     * 保存ボタン押下時の処理内容
     * */
    private fun saveData(height: String, weight: String, bmi: String, comment: String) {
        // 連打出来ないように
        controlUserAction(false)
        // 計算結果をDB登録
        val userDb = UserDbAdapter(this.context!!)
        userDb.add(height, weight, bmi, comment)

        val toast = Toast.makeText(context, "保存しました", Toast.LENGTH_SHORT)
        toast.show()

        Handler().postDelayed({
            // 履歴画面に遷移
            fragmentManager!!.beginTransaction()
                .replace(R.id.frame, HistoryFragment.newInstance())
                .commit()
            controlUserAction(true)
        }, 1000L)
    }

    /**
     * 削除ボタン押下時の処理内容
     * */
    private fun deleteData(key: Array<String>) {
        controlUserAction(false)
        // 履歴画面から選択したレコードを削除する
        val userDb = UserDbAdapter(this.context!!)
        userDb.delete(key)

        input_height.text.clear()
        input_weight.text.clear()
        input_excuse.text.clear()
        result_message.text = ""

        val toast = Toast.makeText(context, "削除しました", Toast.LENGTH_SHORT)
        toast.show()

        Handler().postDelayed({
            // 履歴画面に遷移
            fragmentManager!!.beginTransaction()
                .replace(R.id.frame, HistoryFragment.newInstance())
                .commit()
            controlUserAction(true)
        }, 1000L)

    }

    /**
     * 入力値をチェックする
     * */
    private fun isCorrectInput(a: String, b: String): Boolean {
        var isCorrect = false
        if(!(a.isEmpty() || b.isEmpty())){
            if(!(a == "0" || b == "0")){
                isCorrect = true
            }
        }
        return isCorrect
    }

    /**
     * 身長、体重欄に対する設定
     * */
    private fun configureEditText(editText: EditText) {
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            // TODO 正規表現見直し
            if (source.toString().matches("(^([0-9]{0,3})?(\\.[0-9]?)?$)".toRegex())) source
            else ""
        }
        editText.filters = arrayOf(filter)

    }

    /**
    * 保存・削除ボタン押下時に他の入力欄を制御する
    */
    private fun controlUserAction(isEnable:Boolean){
        save_button.isEnabled = isEnable
        delete_button.isEnabled = isEnable
        input_height.isEnabled = isEnable
        input_weight.isEnabled = isEnable
        calculate_button.isEnabled = isEnable
        input_excuse.isEnabled = isEnable
    }

    private fun prepareForDelete(){
        delete_button.visibility = View.VISIBLE
        input_excuse.visibility = View.VISIBLE
        save_button.visibility = View.VISIBLE

        // 削除対象データを入力欄に表示
        input_height.setText(paramHeight,TextView.BufferType.NORMAL)
        input_weight.setText(paramWeight,TextView.BufferType.NORMAL)
        result_message.text = "あなたのBMIは $paramBmi でした。"
        input_excuse.setText(paramComment)

        // 削除ボタンのリスナー設定

        delete_button.setOnClickListener {
            deleteData(arrayOf(paramDate!!))
        }
    }

}
