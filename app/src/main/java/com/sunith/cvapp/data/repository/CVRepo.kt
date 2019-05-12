package com.sunith.cvapp.data.repository

import com.sunith.cvapp.data.models.raw.Raw
import io.reactivex.Single

interface CVRepo {
    fun provideAllCvs(): Single<List<Raw.CV>>
}

