package com.example.paketwisata.model

class ResponModel {
    var success = 0
    lateinit var message :String
    var user = User()
    var pakets: ArrayList<Paket> = ArrayList()
}