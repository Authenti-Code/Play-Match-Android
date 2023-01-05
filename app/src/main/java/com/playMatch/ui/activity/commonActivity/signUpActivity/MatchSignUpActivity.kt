package com.playMatch.ui.activity.commonActivity.signUpActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.playMatch.R
import com.playMatch.databinding.ActivitySelectSportBinding

class MatchSignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectSportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_sign_up)
    }
}