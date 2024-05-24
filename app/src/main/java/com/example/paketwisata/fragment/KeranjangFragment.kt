package com.example.paketwisata.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paketwisata.R
import com.example.paketwisata.R.id.rv_produk
import android.content.Intent
import com.example.paketwisata.adapter.AdapterKeranjang
import com.example.paketwisata.helper.Helper
import com.example.paketwisata.model.Paket
import com.example.paketwisata.room.MyDatabase
import com.example.paketwisata.activity.PembayaranActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_keranjang.*

/**
 * A simple [Fragment] subclass.
 * Use the [RiwayatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KeranjangFragment : Fragment() {

    lateinit var  myDb : MyDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)

        myDb = MyDatabase.getInstance(requireActivity())!!

        mainButton()
        return view
    }

    lateinit var adapter : AdapterKeranjang
    var listPaket = ArrayList<Paket>()

    private fun displayPaket(){
        listPaket = myDb.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        adapter = AdapterKeranjang(requireActivity(), listPaket, object : AdapterKeranjang.Listeners{
            override fun onUpdate() {
                hitungTotal()
            }

            override fun onDelete(position: Int) {
                listPaket.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

        })

        rvProduk.adapter = adapter
        rvProduk.layoutManager = layoutManager
    }

//    var totalHarga = 0
    var totalHarga = 0
    fun hitungTotal(){
        val listPaket = myDb.daoKeranjang().getAll() as ArrayList

         totalHarga = 0
        //var isSelectedAll = true
        for (paket in listPaket) {
            if (paket.selected) {
                val harga = Integer.valueOf(paket.harga)
                totalHarga += (harga * paket.jumlah)
            }//else {
                //isSelectedAll = false
            //}
        }
        //cbAll.isChecked = isSelectedAll
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }


    private fun mainButton(){
        btnDelete.setOnClickListener {
            val listDelete = ArrayList<Paket>()
            for (p in listPaket){
                if (p.selected) listDelete.add(p)
            }
            delete(listDelete)

        }

        btnBayar.setOnClickListener {
            val intent = Intent(requireActivity(), PembayaranActivity::class.java)
            intent.putExtra("extra", "" + totalHarga)
            startActivity(intent)
        }

        //cbAll.setOnClickListener {
        //    for (paket in listPaket.indices){
        //        val paket = listPaket[i]
        //        paket.selected = cbAll.isChecked

        //        listPaket[i] = paket
        //    }
        //    adapter.notifyDataSetChanged()
        //}

    }

    private fun delete(data:  ArrayList<Paket>){
        io.reactivex.disposables.CompositeDisposable()
            .add(io.reactivex.Observable.fromCallable { myDb!!.daoKeranjang().delete(data) }
            .subscribeOn(io.reactivex.schedulers.Schedulers.computation())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
            })
    }

    lateinit var btnDelete: ImageView
    lateinit var rvProduk: RecyclerView
    lateinit var tvTotal: TextView
    lateinit var btnBayar: TextView
    lateinit var cbAll: CheckBox
    private fun init(view: View) {
        btnDelete = view.findViewById(R.id.btn_delete)
        rvProduk = view.findViewById(R.id.rv_produk)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    override fun onResume() {
        displayPaket()
        hitungTotal()
        super.onResume()
    }

}
