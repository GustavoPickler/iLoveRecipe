package com.example.iluvrecipe.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iluvrecipe.controller.adapter.LineAdapter
import com.example.iluvrecipe.databinding.ActivityListBinding
import com.example.iluvrecipe.room.ILuvRecipesDatabase


class Listar : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listaReceitas.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        binding.buttonCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroReceita::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listaReceitas.layoutManager = layoutManager

        val repo = ILuvRecipesDatabase.getInstance(this).receitaDao()

         binding.listaReceitas.adapter = LineAdapter(repo.getAll())
    }
}