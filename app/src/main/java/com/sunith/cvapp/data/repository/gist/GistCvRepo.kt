package com.sunith.cvapp.data.repository.gist

import com.sunith.cvapp.data.models.raw.Raw
import com.sunith.cvapp.data.repository.CVRepo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class GistCVRepo(private val gistApi: GistApi) : CVRepo {
    override fun provideAllCvs(): Single<List<Raw.CV>> {
        return gistApi.getCVList()
    }
}

interface GistApi {
    @GET("31dbf4a4bb52961c7ab5079446efad8a/raw/8c18c1f77964a9f0e9eb505798089e13e555de79/cv_data.json")
    fun getCVList(): Single<List<Raw.CV>>
}

object GistCVModule {

    fun getGistRepo(): CVRepo {
        return GistCVRepo(gistApi())
    }

    private fun gistApi(): GistApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/sunith87/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(GistApi::class.java)
    }
}