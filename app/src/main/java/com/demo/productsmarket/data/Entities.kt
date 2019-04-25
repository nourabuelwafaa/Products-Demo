package com.demo.productsmarket.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerializedName("productDescription")
    val desc: String,
    val image: Image,
    val price: Int
)

data class Image(
    val link: String,
    val height: Int,
    val width: Int
)

data class ProductResponse(
    @SerializedName("data")
    val products: List<Product>

)

object ImageConverter {

    @TypeConverter
    @JvmStatic
    fun getImage(image: String): Image {

        return Gson().fromJson(image, Image::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun setImage(image: Image): String {

        return Gson().toJson(image)
    }


}