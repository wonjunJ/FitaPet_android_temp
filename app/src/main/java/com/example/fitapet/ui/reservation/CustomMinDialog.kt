package com.example.fitapet.ui.reservation

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.fitapet.R

class CustomMinDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.minute_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true)
        dialog.setTitle("시작시간으로부터");
        //dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show()
        val plusbtn:ImageView=dialog.findViewById(R.id.plus_btn)
        val minusbtn:ImageView=dialog.findViewById(R.id.minus_btn)
        val minute:TextView=dialog.findViewById(R.id.minutes)
        val finishBtn:Button=dialog.findViewById(R.id.finish_btn)
//        val edit_name = dialog.findViewById<EditText>(R.id.name_edit)

//        dialog.cancel_button.setOnClickListener {
//            dialog.dismiss()
//        }
        plusbtn.setOnClickListener{
            var preMin:Int=minute.text.toString().toInt()
            if(preMin<360)
                minute.text=(preMin+30).toString()
        }
        minusbtn.setOnClickListener{
            var preMin:Int=minute.text.toString().toInt()
            if(preMin!=0)
                minute.text=(preMin-30).toString()
        }
        finishBtn.setOnClickListener {
            onClickListener.onClicked(minute.text.toString())
            dialog.dismiss()
        }



    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}