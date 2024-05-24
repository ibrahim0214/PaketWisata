package com.example.paketwisata.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.paketwisata.R
import com.example.paketwisata.helper.Helper
import com.example.paketwisata.model.Paket
import com.example.paketwisata.room.MyDatabase
import com.example.paketwisata.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_paket.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailPaketActivity : AppCompatActivity() {

    lateinit var myDb: MyDatabase
    lateinit var paket: Paket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_paket)

        myDb = MyDatabase.getInstance(this)!! // call database

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton(){
        btn_keranjang.setOnClickListener {

            val data = myDb.daoKeranjang().getpaket(paket.id)
            if (data == null){
                insert()
            }else {
                data.jumlah += 1
                update(data)
            }

        }

        btn_favorit.setOnClickListener {
            val listData = myDb.daoKeranjang().getAll() // get All data
            for(note :Paket in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }

        btn_toKeranjang.setOnClickListener {
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    private fun insert(){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(paket) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil masuk keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun update(data: Paket){
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this, "Berhasil masuk keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        val dataKeranjang = myDb.daoKeranjang().getAll()

        if(dataKeranjang.isNotEmpty()) {
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo(){
        val data = intent.getStringExtra("extra")
        paket = Gson().fromJson<Paket>(data, Paket::class.java)

        tv_nama.text = paket.name
        tv_harga.text = Helper().gantiRupiah(paket.harga)
        tv_deskripsi.text = paket.deskripsi

        val img = Config.paketUrl + paket.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.slider1)
            .error(R.drawable.slider1)
            .resize(400, 400)
            .into(image)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = paket.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}