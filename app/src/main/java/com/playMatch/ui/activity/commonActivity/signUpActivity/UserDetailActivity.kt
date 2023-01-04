package com.playMatch.ui.activity.commonActivity.signUpActivity

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivitySignUpBinding
import com.playMatch.databinding.ActivityUserDetailBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity
import com.playMatch.ui.activity.commonActivity.loginActivity.LoginActivity
import java.util.*

class UserDetailActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserDetailBinding
    private var cal: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.Continue.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.dob.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.beginner -> {
                binding.beginnerCheck.visibility=View.VISIBLE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.GONE
            }
            R.id.intermediate -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.VISIBLE
                binding.experiencedCheck.visibility=View.GONE
            }
            R.id.experienced -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.VISIBLE
            }
            R.id.Continue -> {
            CommonUtils.performIntent(this, AddProfileImageActivity::class.java)
        }
            R.id.dob -> {
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    }

                DatePickerDialog(
                    this,R.style.dialogTheme,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dob.text = sdf.format(cal.time)
    }
}