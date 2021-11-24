package com.example.iluvrecipe.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.iluvrecipe.databinding.ActivityMainBinding
import com.example.iluvrecipe.room.ILuvRecipesDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ILuvRecipesDatabase.getInstance(this).userDao()

        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val userAlreadyLoggedIn = sharedPreferences.getLong("id", 0L)

        if (userAlreadyLoggedIn != 0L) {
            val intent = Intent(this, Listar::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            if (binding.nomeUsuario.editText?.text.toString() == "" || binding.password.editText?.text.toString() == "") {
                Toast.makeText(this, "Usuario e senha devem ser preenchidos!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val usuario = repo.findByUsuarioAndPassword(
                binding.nomeUsuario.editText?.text.toString(),
                binding.password.editText?.text.toString()
            )

            if (usuario == null) {
                Toast.makeText(this, "Usuario ou senha incorretos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val editor = sharedPreferences.edit()
            editor.putLong("id", usuario.id)
            editor.apply()

            val intent = Intent(this, Listar::class.java)
            startActivity(intent)
        }

        binding.buttonCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroPessoa::class.java)
            startActivity(intent)
        }

    }
}