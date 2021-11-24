package com.example.iluvrecipe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val username: String,
    val senha: String,
    val email: String)