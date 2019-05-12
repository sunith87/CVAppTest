package com.sunith.cvapp.data

import android.content.Context
import com.sunith.cvapp.data.models.domain.Domain
import com.sunith.cvapp.data.repository.CVRepo
import com.sunith.cvapp.data.repository.asset.AssetCVProviderModule
import com.sunith.cvapp.data.repository.asset.AssetCVRepo
import com.sunith.cvapp.data.repository.gist.GistCVModule
import io.reactivex.Single

interface CVDataProvider {
    fun provideAllCvs(): Single<List<Domain.CVData>>
}

class CVDataProviderImpl(
    private val cvRepo: CVRepo,
    private val domainConverter: DomainConverter
) : CVDataProvider {
    override fun provideAllCvs(): Single<List<Domain.CVData>> {
        return cvRepo.provideAllCvs()
            .map { allCvs -> domainConverter.convert(allCvs) }
    }
}

object CVDataProviderModule {
    fun getCVDataProvider(context: Context): CVDataProvider {
        return CVDataProviderImpl(GistCVModule.getGistRepo(), DomainConverter())
        //return CVDataProviderImpl(AssetCVProviderModule.getAssetCVRepo(context), DomainConverter())
    }
}