package com.graviton.beerwithbros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.graviton.beerwithbros.model.Beer
import com.graviton.beerwithbros.repository.paging.BeerPagingRepository
import com.graviton.beerwithbros.util.Result
import com.graviton.beerwithbros.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val repository: BeerPagingRepository,
) : ViewModel() {
    private var _beersStateFlow = MutableStateFlow<Result<PagingData<Beer>>>(Result.Loading)
    val beersStateFlow : StateFlow<Result<PagingData<Beer>>> = _beersStateFlow

    fun onRefresh() {
        viewModelScope.launch {
            repository.getBeers().asResult()
                .collect { result ->
                    _beersStateFlow.update { result }
                }
        }
    }
}