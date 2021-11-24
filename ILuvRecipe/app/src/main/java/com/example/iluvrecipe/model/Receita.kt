package com.example.iluvrecipe.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Receita(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val nome: String,
    val descricao: String,
    val image: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(nome)
        parcel.writeString(descricao)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Receita> {
        override fun createFromParcel(parcel: Parcel): Receita {
            return Receita(parcel)
        }

        override fun newArray(size: Int): Array<Receita?> {
            return arrayOfNulls(size)
        }
    }
}