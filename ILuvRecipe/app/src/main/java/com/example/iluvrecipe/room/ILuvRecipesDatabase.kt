package com.example.iluvrecipe.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.iluvrecipe.model.Receita
import com.example.iluvrecipe.model.Usuario
import com.example.iluvrecipe.room.dao.ReceitaDao
import com.example.iluvrecipe.room.dao.UsuarioDao

@Database(
    entities = [Usuario::class, Receita::class],
    version = 1,
    exportSchema = false
)

abstract class ILuvRecipesDatabase : RoomDatabase() {
    abstract fun userDao() : UsuarioDao
    abstract fun receitaDao() : ReceitaDao

    companion object {

        const val NOME_BANCO_DADOS: String = "reciclo.db"

        fun getInstance(context: Context) : ILuvRecipesDatabase {
            return Room.databaseBuilder(context, ILuvRecipesDatabase::class.java, NOME_BANCO_DADOS)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}