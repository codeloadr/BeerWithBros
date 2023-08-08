package com.graviton.beerwithbros.repository.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.graviton.beerwithbros.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BeerPagingRepository @Inject constructor(
    private val pagingSource: BeerPagingSource,
) {
    fun getBeers() : Flow<PagingData<Beer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = true),
            pagingSourceFactory = {pagingSource}
            ).flow.flowOn(Dispatchers.IO)
    }
}