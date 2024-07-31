package com.target.targetcasestudy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.DealResponse
import com.target.targetcasestudy.api.DealsRepository
import com.target.targetcasestudy.data.DealItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
private val dealsRepository: DealsRepository
): ViewModel() {
    private val _dealsResponse = MutableLiveData<DealResponse?>(null)
    val dealsResponse: LiveData<DealResponse?> = _dealsResponse

    private val _itemDetailsResponse = MutableLiveData<Deal?>(null)
    val itemDetailsResponse: LiveData<Deal?> = _itemDetailsResponse

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = dealsRepository.retrieveDeals()
                _dealsResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchItem(id: String) {
        viewModelScope.launch {
            try {
                val response = dealsRepository.retrieveItemDetails(id)
                _itemDetailsResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}