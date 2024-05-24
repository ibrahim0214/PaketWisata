package com.example.paketwisata

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.paketwisata.activity.LoginActivity
import com.example.paketwisata.activity.MasukActivity
import com.example.paketwisata.fragment.AkunFragment
import com.example.paketwisata.fragment.HomeFragment
import com.example.paketwisata.fragment.KeranjangFragment
import com.example.paketwisata.helper.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class dashboard : AppCompatActivity() {

    private val fragmentHome: Fragment = HomeFragment()
    private val fragmentRiwayat: Fragment = KeranjangFragment()
    private val fragmentAkun: Fragment = AkunFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentHome

    private lateinit var  menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin = false

    private lateinit var s: SharedPref

    private var dariDetail :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        s = SharedPref(this)

        setUpBottomNav()

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessage, IntentFilter("event:keranjang"))
    }

    val mMessage : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            dariDetail = true
        }
    }

    fun setUpBottomNav(){

        fm.beginTransaction().add(R.id.container, fragmentHome).show(fragmentHome).commit()
        fm.beginTransaction().add(R.id.container, fragmentRiwayat).hide(fragmentRiwayat).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when(item.itemId) {
                R.id.navigation_home -> {
                    callFragment(0, fragmentHome)
                }
                R.id.navigation_keranjang -> {
                    callFragment(1, fragmentRiwayat)
                }
                R.id.navigation_akun -> {
                    if (s.getStatusLogin()) {
                        callFragment(2, fragmentAkun)
                    } else {
                        startActivity(Intent(this, MasukActivity::class.java))
                    }

                }

            }

            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onResume() {
        if (dariDetail) {
            dariDetail = false
            callFragment(1, fragmentRiwayat)
        }
        super.onResume()
    }
}