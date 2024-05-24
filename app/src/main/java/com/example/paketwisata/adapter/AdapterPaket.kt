package com.example.paketwisata.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.paketwisata.R
import com.example.paketwisata.activity.DetailPaketActivity
import com.example.paketwisata.activity.LoginActivity
import com.example.paketwisata.dashboard
import com.example.paketwisata.helper.Helper
import com.example.paketwisata.model.Paket
import com.example.paketwisata.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterPaket(var activity: Activity, var data:ArrayList<Paket>):RecyclerView.Adapter<AdapterPaket.Holder>() {

    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga = view.findViewById<TextView>(R.id.tv_harga)
        val imgProduk = view.findViewById<ImageView>(R.id.img_produk)
        val layout1 = view.findViewById<CardView>(R.id.layout1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text = data[position].name
        holder.tvHarga.text = Helper().gantiRupiah(data[position].harga)
//        holder.imgProduk.setImageResource(data[position].image)
        val image = Config.paketUrl + data[position].image
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.slider1)
            .error(R.drawable.slider1)
            .into(holder.imgProduk)

        holder.layout1.setOnClickListener {
            val activiti = Intent(activity, DetailPaketActivity::class.java)
            val str = Gson().toJson(data[position], Paket::class.java)
            activiti.putExtra("extra", str)

            activity.startActivity(activiti)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}