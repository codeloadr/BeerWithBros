package com.graviton.beerwithbros.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.graviton.beerwithbros.api.BwbApi
import com.graviton.beerwithbros.model.Beer
import retrofit2.HttpException
import java.io.IOException
import java.lang.Integer.max
import javax.inject.Inject

/**
 * https://proandroiddev.com/infinite-scrolling-with-android-paging-library-and-flow-api-e017f47517d6
 *
 */
class BeerPagingSource @Inject constructor(
    private val bwbApi: BwbApi,
): PagingSource<Int, Beer>() {

    companion object {
        const val STARTING_KEY = 1
    }
    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        // Start paging with the STARTING_KEY if this is the first load
        val start = params.key ?: STARTING_KEY
        // Load as many items as hinted by params.loadSize
        val range = start.until(start + params.loadSize)
        return try {
            val beers =  bwbApi.getBeers(page = start, pageSize = params.loadSize)
            page(beers, start, range, params)
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    private fun page(
        it: List<Beer>,
        start: Int,
        range: IntRange,
        params: LoadParams<Int>
    ): LoadResult<Int, Beer> {
        return LoadResult.Page(
            data = it,
            // Make sure we don't try to load items behind the STARTING_KEY
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}