package com.example.paketwisata.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.paketwisata.R
import com.example.paketwisata.adapter.AdapterPaket
import com.example.paketwisata.adapter.AdapterSlider
import com.example.paketwisata.app.ApiConfig
import com.example.paketwisata.dashboard
import com.example.paketwisata.model.Paket
import com.example.paketwisata.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var vpSlider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlasir: RecyclerView
    lateinit var rvElektronik: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getPaket()


        return view
    }

    fun displayPaket(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider1)
        arrSlider.add(R.drawable.slider2)
        arrSlider.add(R.drawable.slider3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterPaket(requireActivity(), listPaket)
        rvProduk.layoutManager = layoutManager

        rvProdukTerlasir.adapter = AdapterPaket(requireActivity(), listPaket)
        rvProdukTerlasir.layoutManager = layoutManager2

        rvElektronik.adapter = AdapterPaket(requireActivity(), listPaket)
        rvElektronik.layoutManager = layoutManager3
    }

    private var listPaket:ArrayList<Paket> = ArrayList()
    fun getPaket(){
        ApiConfig.instanceRetrofit.getPaket().enqueue(object : retrofit2.Callback<ResponModel>{

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listPaket = res.pakets
                    displayPaket()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

        })
    }

    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlasir = view.findViewById(R.id.rv_produkTerlasir)
        rvElektronik = view.findViewById(R.id.rv_elektronik)
    }






//
//    val arrPaket: ArrayList<Paket>get(){
//        val arr = ArrayList<Paket>()
//        val p1 = Paket()
//            p1.nama = "HP 14_bs749tu"
//            p1.harga = "Rp.5.500.000"
//            p1.gambar = R.drawable.slider1
//
//        val p2 = Paket()
//            p2.nama = "Hp Envy_13_aq0019tx"
//            p2.harga = "Rp.15.980.000"
//            p2.gambar = R.drawable.slider2
//
//            val p3 = Paket()
//            p3.nama = "HP pavilion_13_an0006na"
//            p3.harga = "Rp.14.200.000"
//            p3.gambar = R.drawable.slider3
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }
//
//    val arrElektronik: ArrayList<Paket>get(){
//        val arr = ArrayList<Paket>()
//        val p1 = Paket()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.slider1
//
//        val p2 = Paket()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.slider2
//
//        val p3 = Paket()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.slider3
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }
//
//    val arrProdukTerlaris: ArrayList<Paket>get(){
//        val arr = ArrayList<Paket>()
//        val p1 = Paket()
//        p1.nama = "HP 14_bs749tu"
//        p1.harga = "Rp.5.500.000"
//        p1.gambar = R.drawable.slider1
//
//        val p2 = Paket()
//        p2.nama = "Hp Envy_13_aq0019tx"
//        p2.harga = "Rp.15.980.000"
//        p2.gambar = R.drawable.slider2
//
//        val p3 = Paket()
//        p3.nama = "HP pavilion_13_an0006na"
//        p3.harga = "Rp.14.200.000"
//        p3.gambar = R.drawable.slider3
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//
//        return arr
//    }
}