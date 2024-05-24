package com.example.paketwisata.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.paketwisata.R
import com.example.paketwisata.activity.LoginActivity
import com.example.paketwisata.dashboard
import com.example.paketwisata.helper.SharedPref


/**
 * A simple [Fragment] subclass.
 * Use the [AkunFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AkunFragment : Fragment() {
    lateinit var s: SharedPref
    lateinit var btnLogout: Button
    lateinit var tvNama: TextView
    lateinit var tvEmail: TextView
    lateinit var tvPhone: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_akun, container, false)
        init(view)

        s = SharedPref(requireActivity())

        btnLogout.setOnClickListener {
            s.setStatusLogin(false)
        }
        setData()
        return view
    }

    fun setData(){

        if (s.getUser() == null){
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            return
        }

        val user = s.getUser()!!

        tvNama.text = user.name
        tvEmail.text = user.email
        tvPhone.text = user.phone
    }

    private fun init(view: View) {
        btnLogout = view.findViewById(R.id.btn_logout)
        tvNama = view.findViewById(R.id.tvnama)
        tvEmail = view.findViewById(R.id.tvemail)
        tvPhone = view.findViewById(R.id.tvphone)
    }
}