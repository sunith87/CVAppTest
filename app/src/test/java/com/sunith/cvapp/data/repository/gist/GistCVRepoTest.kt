package com.sunith.cvapp.data.repository.gist

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunith.cvapp.data.models.raw.Raw
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GistCVRepoTest {

    @Mock
    private lateinit var mockCVList: List<Raw.CV>
    @Mock
    private lateinit var mockGistApi: GistApi
    private lateinit var gistCVRepo: GistCVRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        gistCVRepo = GistCVRepo(mockGistApi)
    }

    @Test
    fun testProvideAllCvs_shouldCallGistCVRepoGetCVList() {
        whenever(mockGistApi.getCVList()).thenReturn(Single.just(mockCVList))

        gistCVRepo.provideAllCvs()

        verify(mockGistApi).getCVList()
    }

    @Test
    fun testProvideAllCvs_shouldReturnCVList_whenCallSuccessful() {
        whenever(mockGistApi.getCVList()).thenReturn(Single.just(mockCVList))

        gistCVRepo.provideAllCvs().test().assertValue(mockCVList)
    }

    @Test
    fun testProvideAllCvs_shouldReturnError_whenCallFails() {
        val mockThrowable = Throwable()
        whenever(mockGistApi.getCVList()).thenReturn(Single.error(mockThrowable))

        gistCVRepo.provideAllCvs().test().assertError(mockThrowable)
    }
}