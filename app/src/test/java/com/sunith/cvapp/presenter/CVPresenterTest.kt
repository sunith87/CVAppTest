package com.sunith.cvapp.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunith.cvapp.data.CVDataProvider
import com.sunith.cvapp.data.models.domain.Domain
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CVPresenterTest {

    @Mock
    private lateinit var mockCompositeDisposible: CompositeDisposable
    @Mock
    private lateinit var mockAllCvs: List<Domain.CVData>
    @Mock
    private lateinit var mockView: CVContract.View
    @Mock
    private lateinit var mockRxModule: RxModule
    @Mock
    private lateinit var mockCvDataProvider: CVDataProvider

    private lateinit var cVPresenter: CVPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(mockRxModule.ioScheduler()).thenReturn(Schedulers.trampoline())
        whenever(mockRxModule.mainScheduler()).thenReturn(Schedulers.trampoline())
        whenever(mockRxModule.compositeDisposable()).thenReturn(mockCompositeDisposible)
        whenever(mockCvDataProvider.provideAllCvs()).thenReturn(Single.just(mockAllCvs))

        cVPresenter = CVPresenter(mockCvDataProvider, mockRxModule)
    }

    @Test
    fun testFetchData_shouldCallShowProgressView() {
        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockView).showProgressView()
    }

    @Test
    fun testFetchData_shouldAddFetchCvsSingleToCoimpositeDisposable() {
        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockCompositeDisposible).add(any())
    }

    @Test
    fun testFetchData_shouldCallProvideAllsCvs() {
        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockCvDataProvider).provideAllCvs()
    }

    @Test
    fun testFetchData_shouldHideProgressView_whenCallSuccessful() {
        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockView).hideProgressView()
    }

    @Test
    fun testFetchData_shouldRenderCvs_whenCallSuccessful() {
        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockView).renderCVs(mockAllCvs)
    }

    @Test
    fun testFetchData_shouldHideProgressView_whenCallFailed() {
        whenever(mockCvDataProvider.provideAllCvs()).thenReturn(Single.error(Throwable()))

        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockView).hideProgressView()
    }

    @Test
    fun testFetchData_shouldHCallError_whenCallFailed() {
        val mockMessage = "mockMessage"
        whenever(mockCvDataProvider.provideAllCvs()).thenReturn(Single.error(Throwable(mockMessage)))

        cVPresenter.onResume(mockView)
        cVPresenter.fetchData()

        verify(mockView).error(mockMessage)
    }

    @Test
    fun testOnPause_shouldDisposeCompositeDisposable() {
        whenever(mockCompositeDisposible.isDisposed).thenReturn(false)

        cVPresenter.onPause()

        verify(mockCompositeDisposible).dispose()
    }

    @Test
    fun testOnDestroy_shouldClearCompositeDisposable() {
        cVPresenter.onDestroy()

        verify(mockCompositeDisposible).clear()
    }
}