package com.example.iluvrecipe.room.dao

import androidx.room.*
import com.example.iluvrecipe.model.Receita

@Dao
interface ReceitaDao {

    @Insert
    fun insert(receita: Receita): Long;

    @Update
    fun update(receita: Receita): Void;

    @Delete
    fun delete(receita: Receita): Void;

    @Query("SELECT * FROM receita")
    fun getAll() : List<Receita>
}