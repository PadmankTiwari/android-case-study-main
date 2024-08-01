package com.target.targetcasestudy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.DealResponse
import com.target.targetcasestudy.api.DealsRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var dealsRepository: DealsRepository

    private lateinit var viewModel: ItemViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = createItemViewModel(dealsRepository)
    }

    private fun createItemViewModel(repository: DealsRepository) =
        ItemViewModel(dealsRepository = repository)


    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchData sets isLoading to true and updates dealsResponse on success`() = runTest {
        // Arrange
        val mockResponse = DealResponse(emptyList())
        coEvery { dealsRepository.retrieveDeals() } returns (mockResponse)

        // Act
        viewModel.fetchData()

        // Assert
        testDispatcher.scheduler.advanceUntilIdle() // Advance coroutine dispatcher

        assertThat(viewModel.dealsResponse.value).isEqualTo(mockResponse)
        assertThat(viewModel.isLoading.value).isFalse()
        assertThat(viewModel.errorMessage.value).isNull()
    }

    @Test
    fun `fetchData sets errorMessage on failure`() = runTest {
        // Arrange
        coEvery { dealsRepository.retrieveDeals() } throws RuntimeException("Error")

        // Act
        viewModel.fetchData()

        // Assert
        testDispatcher.scheduler.advanceUntilIdle() // Advance coroutine dispatcher

        assertThat(viewModel.dealsResponse.value).isNull()
        assertThat(viewModel.isLoading.value).isFalse()
        assertThat(viewModel.errorMessage.value).isEqualTo("Failed to fetch deals. Please try again.")
    }

    @Test
    fun `fetchItem sets isLoading to true and updates itemDetailsResponse on success`() = runTest {
        // Arrange
        val mockDeal = Deal("1", "Test Deal")
        coEvery { dealsRepository.retrieveItemDetails(any()) } returns (mockDeal)

        // Act
        viewModel.fetchItem("1")

        // Assert
        testDispatcher.scheduler.advanceUntilIdle() // Advance coroutine dispatcher

        assertThat(viewModel.itemDetailsResponse.value).isEqualTo(mockDeal)
        assertThat(viewModel.isLoading.value).isFalse()
        assertThat(viewModel.errorMessage.value).isNull()
    }

    @Test
    fun `fetchItem sets errorMessage on failure`() = runTest {
        // Arrange
        coEvery { dealsRepository.retrieveItemDetails(any()) } throws RuntimeException("Error")

        // Act
        viewModel.fetchItem("1")

        // Assert
        testDispatcher.scheduler.advanceUntilIdle() // Advance coroutine dispatcher

        assertThat(viewModel.itemDetailsResponse.value).isNull()
        assertThat(viewModel.isLoading.value).isFalse()
        assertThat(viewModel.errorMessage.value).isEqualTo("Failed to fetch deal details. Please try again.")
    }
}