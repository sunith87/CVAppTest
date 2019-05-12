package com.sunith.cvapp.data.repository.asset

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sunith.cvapp.data.models.raw.Raw
import com.sunith.cvapp.data.repository.CVRepo
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class AssetCVRepo(
    private val context: Context,
    private val gson: Gson
) : CVRepo {

    override fun provideAllCvs(): Single<List<Raw.CV>> {
        val inputStream = context.assets.open("allData.json")
        val data = inputStream.convertToString()
        return Single.just(convertDataToDomainObject(data))
    }

    private fun convertDataToDomainObject(data: String): List<Raw.CV> {
        val allCVsDataTypeToken = object : TypeToken<List<Raw.CV>>() {}.type
        return gson.fromJson<List<Raw.CV>>(data, allCVsDataTypeToken)
    }
}

fun InputStream.convertToString(): String {
    val inputStreamReader = InputStreamReader(this)
    val reader = BufferedReader(inputStreamReader)

    val stringBuilder = StringBuilder()

    reader.forEachLine { stringBuilder.append(it) }

    return stringBuilder.toString()
}

object AssetCVProviderModule {
    fun getAssetCVRepo(context: Context): CVRepo {
        return AssetCVRepo(context, Gson())
    }
}