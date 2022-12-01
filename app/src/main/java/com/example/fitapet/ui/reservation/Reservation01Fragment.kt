package com.example.fitapet.ui.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.fitapet.databinding.FragmentReservation01Binding
import java.util.*


class Reservation01Fragment : Fragment(){
    //onCreate
    private var _binding: FragmentReservation01Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val today = GregorianCalendar()
        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH)
        val date: Int = today.get(Calendar.DATE)
        _binding = FragmentReservation01Binding.inflate(inflater,container,false)
        //날짜 받기
        binding.reservation01SelectDayBtn.setOnClickListener{
            binding.reservation01SelectDayBtn.isSelected=binding.reservation01SelectDayBtn.isSelected!=true
            val dlg = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Log.d("kim","${year}년 ${month+1}월 ${dayOfMonth}일")
                    binding.reservation01SelectDayBtn.text="${month+1}월 ${dayOfMonth}일"
                    binding.reservation01SelectDayBtn.isSelected=false
                }
            }, year, month, date)
            dlg.show()
        }
        val mHour: Int = today.get(Calendar.HOUR_OF_DAY)
        val mMinute: Int = today.get(Calendar.MINUTE)
//        var ampm: String="오전"
        //시간받기
        binding.reservation01SelectTimeBtn.setOnClickListener {
            binding.reservation01SelectTimeBtn.isSelected=binding.reservation01SelectTimeBtn.isSelected!=true
            val timedlg = TimePickerDialog(requireContext(),android.R.style.Theme_Holo_Light_Dialog_NoActionBar,object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                    Log.d("timeCheck",""+p1)

                    binding.reservation01SelectTimeBtn.text="${p1}시 ${p2}분"
                    binding.reservation01SelectTimeBtn.isSelected=false
                    binding.reservation01Arrow.visibility=View.VISIBLE
                    binding.reservation01SelectTimeBtn02.visibility=View.VISIBLE
                }

            },mHour,mMinute,false)
            timedlg.setTitle("돌봄시작시간");
            timedlg.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            timedlg.show()
        }
        //몇분할건지 받기
        binding.reservation01SelectTimeBtn02.setOnClickListener {
            val dialog = CustomMinDialog(requireContext())
            dialog.showDialog()

            dialog.setOnClickListener(object : CustomMinDialog.OnDialogClickListener {
                override fun onClicked(str: String)
                {
                    binding.reservation01SelectTimeBtn02.text = "시작시간으로부터 ${str}분"
                }

            })
        }
        //픽업여부받기
        binding.reservation01PickupBtnY.setOnClickListener {
            binding.reservation01PickupBtnY.isSelected=binding.reservation01PickupBtnY.isSelected!=true
            binding.reservation01PickupBtnN.isSelected=false
        }
        binding.reservation01PickupBtnN.setOnClickListener {
            binding.reservation01PickupBtnN.isSelected=binding.reservation01PickupBtnN.isSelected!=true
            binding.reservation01PickupBtnY.isSelected=false
        }
        //edit text focus풀기
        binding.reservation01ParentLayout.setOnTouchListener(OnTouchListener { v, event ->
            hideKeyboard()
            binding.reservation01RequestEditText.clearFocus()
            false
        })
        return binding.root
    }
    private fun hideKeyboard() {
        if (requireActivity() != null && requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}