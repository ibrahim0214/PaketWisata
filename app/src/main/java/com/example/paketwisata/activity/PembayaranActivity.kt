package com.example.paketwisata.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paketwisata.R
import android.content.Intent
import android.widget.Toast
import com.example.paketwisata.helper.Helper
import com.example.paketwisata.helper.SharedPref
import com.example.paketwisata.model.Checkout
import com.example.paketwisata.room.MyDatabase
import com.example.paketwisata.app.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.paketwisata.model.ResponModel
import kotlinx.android.synthetic.main.activity_pembayaran.*

class PembayaranActivity : AppCompatActivity() {

    lateinit var  myDb : MyDatabase

    var totalHarga = 0
    var totalOngkir = 2200
    var totalPendapatan = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        myDb = MyDatabase.getInstance(this)!!

        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        tvTotalSetoran.text = com.example.paketwisata.helper.Helper().gantiRupiah(totalHarga)
        tvOngkir.text = Helper().gantiRupiah(totalOngkir)
        hitungTotal()
        mainButton()

    }

    fun hitungTotal(){
        totalPendapatan = (totalHarga - totalOngkir)
        tvTotal.text = Helper().gantiRupiah(totalPendapatan)

    }

    private fun mainButton(){
        btnPickup.setOnClickListener{
            bayar()
        }
    }

    private fun bayar(){
        val user = SharedPref(this).getUser()!!
       // val a = myDb.DaoAlamat().getByStatus(true)!!

        val listPaket = myDb.daoKeranjang().getAll() as ArrayList

        var totalItem = 0
        var totalHarga = 0
        val pakets = ArrayList<com.example.paketwisata.model.Checkout.Item>()
        for (p in listPaket){
            if(p.selected){
                totalItem += p.jumlah
                totalHarga += (p.jumlah + Integer.valueOf(p.harga))

                val paket = Checkout.Item()
                paket.id = "" + p.id
                paket.total_item = "" + p.jumlah
                paket.total_harga  = "" + (p.jumlah + Integer.valueOf(p.harga))
                paket.catatan = "Catatan"

                pakets.add(paket)
            }
        }

        val checkout = Checkout()
        checkout.user_id = "" + user.id
        checkout.total_item = "" + totalItem
        checkout.total_harga = "" + totalHarga
       // checkout.name = a.name
       // checkout.phone = a.phone
        checkout.produks = pakets

        com.example.paketwisata.app.ApiConfig.instanceRetrofit.checkout(checkout).enqueue(object :
            Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@PembayaranActivity,"Error :" +t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon = response.body()!!

                if (respon.success == 1) {
                    Toast.makeText(this@PembayaranActivity,"Berhasil kirim ke server", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PembayaranActivity,respon.message, Toast.LENGTH_SHORT).show()
                }
            }


        })
    }

    fun setTotal(){
        tvTotal.text= Helper().gantiRupiah(totalHarga + totalOngkir)
    }


}