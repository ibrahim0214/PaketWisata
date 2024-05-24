package com.example.paketwisata.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paketwisata.R
import android.content.Intent
import com.example.paketwisata.helper.SharedPref
import kotlinx.android.synthetic.main.activity_masuk.*


class MasukActivity : AppCompatActivity() {

    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        s = SharedPref(this)
        mainButton()
        }

    private fun mainButton() {
        btn_prosesLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}





