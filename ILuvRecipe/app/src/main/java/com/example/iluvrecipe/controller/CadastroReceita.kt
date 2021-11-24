package com.example.iluvrecipe.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.iluvrecipe.R
import com.example.iluvrecipe.databinding.ActivityCadastroReceitaBinding
import com.example.iluvrecipe.model.Receita
import com.example.iluvrecipe.room.ILuvRecipesDatabase
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class CadastroReceita : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroReceitaBinding
    private val SELECT_IMAGE = 123

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_receita)
        binding = ActivityCadastroReceitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val repo = ILuvRecipesDatabase.getInstance(this).receitaDao()

        val receita = intent.getParcelableExtra<Receita>("receita")

        if (receita == null) {
            binding.buttonDeletar.visibility = View.GONE
        }

        if (receita != null) {
            binding.nomeReceitaCadastro.setText(receita.nome)
            binding.descricaoReceitaCadastro.setText(receita.descricao)

            val byteArray = Base64.getDecoder().decode(receita.image)
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.imagemReceitaCadastro.setImageBitmap(bmp)

            binding.cadastroReceitaTitulo.text = "Editar Receita"
            binding.buttonCadastrar.text = "Editar"
        }

        binding.buttonCadastrar.setOnClickListener {
            if (binding.nomeReceita.editText?.text.toString() == ""
                || binding.descricaoReceita.editText?.text.toString() == ""
            ) {
                Toast.makeText(
                    this,
                    "É necessário preencher todos os campos para prosseguir!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var withoutImage = true

            if (binding.imagemReceitaCadastro.scaleType == ImageView.ScaleType.FIT_XY) {
                withoutImage = false
            }

            var imageUrl = ""

            if (!withoutImage) {
                val bitmap = (binding.imagemReceitaCadastro.drawable as BitmapDrawable).bitmap
                imageUrl = encodeImage(bitmap)
            }

            if (receita != null) {
                repo.update(
                    Receita(
                        receita.id,
                        binding.nomeReceita.editText?.text.toString(),
                        binding.descricaoReceitaCadastro.editableText?.trimEnd().toString(),
                        imageUrl
                    )
                )
            } else {
                repo.insert(
                    Receita(
                        0,
                        binding.nomeReceita.editText?.text.toString(),
                        binding.descricaoReceitaCadastro.editableText?.trimEnd().toString(),
                        imageUrl
                    )
                )
            }

            val intent = Intent(this, Listar::class.java)
            startActivity(intent)
        }

        binding.imagemReceitaCadastro.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
        }

        binding.buttonDeletar.setOnClickListener {
            if (receita != null) {
                repo.delete(
                    Receita(
                        receita.id,
                        binding.nomeReceita.editText?.text.toString(),
                        binding.descricaoReceitaCadastro.editableText?.trimEnd().toString(),
                        ""
                    )
                )
            }

            val intent = Intent(this, Listar::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        setImage(data.data)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setImage(uri: Uri?) {
        binding.imagemReceitaCadastro.setImageURI(uri)
        binding.imagemReceitaCadastro.scaleType = ImageView.ScaleType.FIT_XY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b);
    }
}