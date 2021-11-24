package com.example.iluvrecipe.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.iluvrecipe.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun insert(usuario: Usuario): Long;

    @Query("SELECT * FROM usuario WHERE username=:username AND senha=:password")
    fun findByUsuarioAndPassword(username: String, password: String): Usuario?
}