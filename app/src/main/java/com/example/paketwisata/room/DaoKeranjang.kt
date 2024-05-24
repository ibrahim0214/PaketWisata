package com.example.paketwisata.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.paketwisata.model.Paket

@Dao
interface DaoKeranjang {

    @Insert(onConflict = REPLACE)
    fun insert(data: Paket)

    @Delete
    fun delete(data: Paket)

    @Delete
    fun delete(data: List<Paket>)

    @Update
    fun update(data: Paket): Int

    @Query("SELECT * from keranjang ORDER BY id ASC")
    fun getAll(): List<Paket>

    @Query("SELECT * FROM keranjang WHERE id = :id LIMIT 1")
    fun getpaket(id: Int): Paket

    @Query("DELETE FROM keranjang")
    fun deleteAll(): Int
}