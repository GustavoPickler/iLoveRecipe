package com.example.iluvrecipe.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.iluvrecipe.R
import com.example.iluvrecipe.databinding.ActivityCadastroPessoaBinding
import com.example.iluvrecipe.model.Usuario
import com.example.iluvrecipe.room.ILuvRecipesDatabase

class CadastroPessoa : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroPessoaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroPessoaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true);

        val repository = ILuvRecipesDatabase.getInstance(this).userDao()

        binding.buttonCadastrar.setOnClickListener {
            if (binding.nomeUsuario.editText?.text.toString() == ""
                || binding.passwordUsuario.editText?.text.toString() == ""
                || binding.confirmPassword.editText?.text.toString() == ""
                || binding.emailUsuario.editText?.text.toString() == "") {
                Toast.makeText(this, "É necessário preencher todos os campos para prosseguir!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.passwordUsuario.editText?.text.toString() != binding.confirmPassword.editText?.text.toString()) {
                Toast.makeText(this, "Os campos de senha devem ser iguais!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(0, binding.nomeUsuario.editText?.text.toString(),
                binding.passwordUsuario.editText?.text.toString(),
                binding.emailUsuario.editText?.text.toString())

            repository.insert(usuario);
            val intent  = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}