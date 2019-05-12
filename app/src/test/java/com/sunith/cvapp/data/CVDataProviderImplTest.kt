package com.sunith.cvapp.data

import com.nhaarman.mockitokotlin2.whenever
import com.sunith.cvapp.data.models.domain.Domain
import com.sunith.cvapp.data.models.raw.Raw
import com.sunith.cvapp.data.repository.CVRepo
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

class CVDataProviderImplTest {

    @Mock
    private lateinit var mockCvList: List<Raw.CV>
    @Mock
    private lateinit var mockDomainCvList: List<Domain.CVData>
    @Mock
    private lateinit var mockConverter: DomainConverter
    @Mock
    private lateinit var mockRepo: CVRepo
    private lateinit var cvDataProvider: CVDataProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(mockRepo.provideAllCvs()).thenReturn(Single.just(mockCvList))
        whenever(mockConverter.convert(mockCvList)).thenReturn(mockDomainCvList)
        cvDataProvider = CVDataProviderImpl(mockRepo, mockConverter)
    }

    @Test
    fun testProvideAllCvs_shouldReturnDomainCvs_whenCallSuccessful() {
        cvDataProvider.provideAllCvs().test().assertValue(mockDomainCvList)
    }

    @Test
    fun testProvideAllCvs_shouldReturnError_whenCallFails() {
        val mockThrowable = RuntimeException()
        whenever(mockRepo.provideAllCvs()).thenReturn(Single.error(mockThrowable))

        cvDataProvider.provideAllCvs().test().assertError(mockThrowable)
    }

    @Test
    fun testProvideAllCvs_shouldReturnError_whenDomainConversionFails() {
        val mockThrowable = RuntimeException()
        whenever(mockConverter.convert(mockCvList)).thenThrow(mockThrowable)

        cvDataProvider.provideAllCvs().test().assertError(mockThrowable)
    }
}